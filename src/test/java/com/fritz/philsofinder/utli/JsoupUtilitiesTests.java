package com.fritz.philsofinder.utli;

import static org.junit.Assert.assertEquals;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.fritz.philsofinder.util.JsoupUtilities;

public class JsoupUtilitiesTests {
	
	@Test
	public void testGetPageName() {
		Document testDoc = Jsoup.parse("<html><title>Test Title</title></html>");
		
		assertEquals("Test Title", JsoupUtilities.getPageName(testDoc));
	}
	
	@Test
	public void testGetFirstLinkWithoutParenthesisOrItalics() {
		Document testDoc = Jsoup.parse("<html><body><div id=\"mw-content-text\"><p>paragraph one <a href=\"firstLink\" /><a href=\"secondLink\" /></p></div></body></html>");
		Element link = JsoupUtilities.getFirstValidLink(testDoc);
		
		assertEquals("firstLink", link.attr("href"));
	}
	
	@Test
	public void testGetFirstLinkWithoutParenthesisOrItalicsWithLinksInSecondParagraph() {
		Document testDoc = Jsoup.parse("<html><body><div id=\"mw-content-text\"><p>paragraph one</p><p>paragraph two <a href=\"firstLink\" /><a href=\"secondLink\" /></p></div><</body></html>");
		Element link = JsoupUtilities.getFirstValidLink(testDoc);
		
		assertEquals("firstLink", link.attr("href"));
	}
	
	@Test
	public void testGetFirstLinkWithoutParenthesisOrItalicsWithLinksInFirstParagraph() {
		Document testDoc = Jsoup.parse("<html><body><div id=\"mw-content-text\"><p>paragraph one <a href=\"firstLink\" /><a href=\"secondLink\" /></p><p>paragraph two</p></div><</body></html>");
		Element link = JsoupUtilities.getFirstValidLink(testDoc);
		
		assertEquals("firstLink", link.attr("href"));
	}
	
	@Test
	public void testGetFirstLinkWithParenthesisNoItalics() {
		Document testDoc = Jsoup.parse("<html><body><div id=\"mw-content-text\"><p>paragraph one (inside parentehsis <a href=\"insideParenthesis\" />)<a href=\"secondLink\" /></p></div><</body></html>");
		Element link = JsoupUtilities.getFirstValidLink(testDoc);
		
		assertEquals("secondLink", link.attr("href"));
	}
	
	@Test
	public void testGetFirstLinkWithoutParentehsisWithItalics() {
		Document testDoc = Jsoup.parse("<html><body><div id=\"mw-content-text\"><p>paragraph one <i><a href=\"insideItalics\" /></i><a href=\"secondLink\" /></p></div><</body></html>");
		Element link = JsoupUtilities.getFirstValidLink(testDoc);
		
		assertEquals("secondLink", link.attr("href"));
	}
	
	@Test
	public void testGetFirstLinkWithParenthesisAndItalics() {
		Document testDoc = Jsoup.parse("<html><body><div id=\"mw-content-text\"><p>paragraph one <i><a href=\"insideItalics\" /></i> (inside parenthesis <a href=\"insideParenthesis\" />) <a href=\"thirdLink\" /></p></div><</body></html>");
		Element link = JsoupUtilities.getFirstValidLink(testDoc);
		
		assertEquals("thirdLink", link.attr("href"));
	}
	
	@Test
	public void testGetFirstLinkWithCiteNote() {
		Document testDoc = Jsoup.parse("<html><body><div id=\"mw-content-text\"><p>paragraph one <a href=\"#cite_note\" /> <a href=\"secondLink\" /></p></div><</body></html>");
		Element link = JsoupUtilities.getFirstValidLink(testDoc);
		
		assertEquals("secondLink", link.attr("href"));
	}

}
