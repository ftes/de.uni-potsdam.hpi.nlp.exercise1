package de.ftes.hpi.nlp.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.ftes.hpi.nlp.languagemodel.Corpus;

public class CorpusParser {	
	private static void recursiveParse(Corpus corpus, Element element, boolean includeTokensWithNonAToZ) {
		NodeList nodeList = element.getChildNodes();
		for (int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if (node instanceof Element) {
				Element el = (Element) node;
				switch (el.getNodeName()) {
				case "sentence":
					corpus.startSentence();
					recursiveParse(corpus, el, includeTokensWithNonAToZ);
					break;
				case "cons":
					corpus.openConstituent(el.getAttribute("cat"));
					recursiveParse(corpus, el, includeTokensWithNonAToZ);
					corpus.closeConstituent();
					break;
				case "tok":
					String text = el.getTextContent();
					if (includeTokensWithNonAToZ || text.matches("[A-Za-z]+")) {
						corpus.addToken(el.getTextContent(), el.getAttribute("cat"));
					}
					break;
				default:
					recursiveParse(corpus, el, includeTokensWithNonAToZ);
				}
			}
		}
	}
	
	public static Corpus parse(File folder, boolean includeTokensWithNonAToZ) throws SAXException, IOException, ParserConfigurationException {
		Corpus corpus = new Corpus();
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		File[] xmlFiles = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		
		for (File file : xmlFiles) {
			Document doc = docBuilder.parse(file);
			recursiveParse(corpus, doc.getDocumentElement(), includeTokensWithNonAToZ);
		}
		
		return corpus;
	}
}
