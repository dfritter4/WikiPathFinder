package com.fritz.philsofinder.util;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

public class JsoupUtilities {
	
	private static final String ARTICLE_CONTENT_ELEMENT_ID = "mw-content-text";
	
	private JsoupUtilities() {
		
	}
	
	public static Document connectAndGetPage(String pageUrl) {
		Document startPage = null;
		
		try {
			startPage = Jsoup.connect(pageUrl).get();
		} catch (IOException e) {
			//would typically have a logger like log4j handle this
			//for using system.err for simplicity
			//
			//same for throwing generic RuntimeException, should 
			//probably define my own exception and handle it but...simplicity
			System.err.println(e);
			throw new RuntimeException("Could not connect to wiki page", e);
		}
		
		return startPage;
	}
	
	public static String getPageName(Document startPage) {
		return StringUtils.replace(startPage.title(), " - Wikipedia", "");
	}
	
	public static Element getFirstValidLink(Document page) {
		Element mainContent = page.getElementById(ARTICLE_CONTENT_ELEMENT_ID);		
		Elements primaryParagraphs = mainContent.getElementsByTag("p");
		
		Element firstParagraphWithLinks;
		
		for(Iterator<Element> paragraphIter = primaryParagraphs.iterator(); paragraphIter.hasNext();) {
			firstParagraphWithLinks = paragraphIter.next();
			Elements links = firstParagraphWithLinks.getElementsByTag("a");
			for(Element link : links) {
				if(isLinkValid(firstParagraphWithLinks, link)) {
					return link;
				}
			}
		}
		return null;
	}
	
	private static Boolean isLinkValid(Element paragraph, Element href) {
		String hrefText = href.attr("href");

		if(!hrefText.contains("#")
				&& !hrefText.contains(":")
				&& !isLinkInsideParenthesis(paragraph, href)
				&& !isLinkInsideItalics(paragraph, href)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private static Boolean isLinkInsideParenthesis(Element paragraph, Element href) {
		String prefix = StringUtils.split(paragraph.html(), href.toString())[0];
		if(StringUtils.countOccurrencesOf(prefix, "(") != StringUtils.countOccurrencesOf(prefix, ")")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private static Boolean isLinkInsideItalics(Element paragraph, Element href) {
		String prefix = StringUtils.split(paragraph.html(), href.toString())[0];
		if(StringUtils.countOccurrencesOf(prefix, "<i>") != StringUtils.countOccurrencesOf(prefix, "</i>")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
