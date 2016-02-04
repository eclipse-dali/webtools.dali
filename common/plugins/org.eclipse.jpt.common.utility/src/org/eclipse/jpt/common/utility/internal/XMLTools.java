/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The XML APIs are a bit awkward, and they like to declare
 * "checked" exceptions (boo). This utility class simplifies using those APIs.
 * In particular, it facilitates the getting and setting of the values
 * of the children of a particular node (e.g. when reading and writing
 * the attributes of an object from and to an XML document).
 */
public final class XMLTools {
	/**
	 * The DOM parser factory.
	 * Lazily initialized.
	 */
	private static DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;

	/**
	 * The DOM parser.
	 * Just keep one around and synchronize access to it.
	 * Lazily initialized.
	 */
	private static DocumentBuilder DOCUMENT_BUILDER;


	/**
	 * The transformer factory.
	 * Lazily initialized.
	 */
	private static TransformerFactory TRANSFORMER_FACTORY;

	/**
	 * The transformer.
	 * Just keep one around and synchronize access to it.
	 * Lazily initialized.
	 */
	private static Transformer TRANSFORMER;


	// ********** parsing **********

	/**
	 * Build and return an XML document based on the contents
	 * of the specified input source.
	 * {@link DocumentBuilder#parse(InputSource inputSource)}
	 * throws {@link RuntimeException}s.
	 */
	public static synchronized Document parse(InputSource inputSource) {
		try {
			return getDocumentBuilder().parse(inputSource);
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Build and return an XML document based on the contents
	 * of the specified reader.
	 */
	public static Document parse(Reader reader) {
		Document document = null;
		try {
			document = parse(new InputSource(reader));
		} finally {
			try {
				reader.close();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return document;
	}

	/**
	 * Build and return an XML document based on the contents
	 * of the specified input stream.
	 */
	public static Document parse(InputStream inputStream) {
		try {
			return parse(new InputStreamReader(inputStream, "UTF-8")); //$NON-NLS-1$
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Build and return an XML document based on the contents
	 * of the specified file.
	 */
	public static Document parse(File file) {
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file), 8192)) { // 8KB
			return parse(inputStream);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static synchronized DocumentBuilder getDocumentBuilder() {
		if (DOCUMENT_BUILDER == null) {
			try {
				DOCUMENT_BUILDER = getDocumentBuilderFactory().newDocumentBuilder();
			} catch (ParserConfigurationException ex) {
				throw new RuntimeException(ex);
			}
		}
		return DOCUMENT_BUILDER;
	}

	/**
	 * See {@link javax.xml.parsers.DocumentBuilderFactory#newInstance()} for
	 * documentation on how the implementation class is determined.
	 */
	private static synchronized DocumentBuilderFactory getDocumentBuilderFactory() {
		if (DOCUMENT_BUILDER_FACTORY == null) {
			DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
		}
		return DOCUMENT_BUILDER_FACTORY;
	}


	// ********** reading DOM **********

	/**
	 * Return the child element node of the specified parent node with
	 * the specified name. Return <code>null</code> if the child is not found.
	 */
	public static Node getChild(Node parent, String childName) {
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if ((child.getNodeType() == Node.ELEMENT_NODE) && child.getNodeName().equals(childName)) {
				return child;
			}
		}
		return null;
	}

	/**
	 * Return all the child element nodes of the specified node.
	 */
	public static Iterable<Node> getChildren(Node node) {
		NodeList children = node.getChildNodes();
		int len = children.getLength();
		ArrayList<Node> result = new ArrayList<>(len);
		for (int i = 0; i < len; i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				result.add(child);
			}
		}
		return result;
	}

	/**
	 * Return all the child element nodes of the specified node
	 * with the specified name.
	 */
	public static Iterable<Node> getChildren(Node node, String childName) {
		NodeList children = node.getChildNodes();
		int len = children.getLength();
		ArrayList<Node> result = new ArrayList<>(len);
		for (int i = 0; i < len; i++) {
			Node child = children.item(i);
			if ((child.getNodeType() == Node.ELEMENT_NODE)
					&& child.getNodeName().equals(childName)) {
				result.add(child);
			}
		}
		return result;
	}

	/**
	 * Return the text content of the specified node.
	 * Throw an exception if the node is not a "simple" node.
	 */
	public static String getTextContent(Node node) {
		NodeList children = node.getChildNodes();
		// <foo></foo> or <foo/>
		if (children.getLength() == 0) {
			return StringTools.EMPTY_STRING;
		}

		// <foo>bar</foo>
		if (children.getLength() == 1) {
			Node child = children.item(0);
			if (child.getNodeType() == Node.TEXT_NODE) {
				return node.getFirstChild().getNodeValue();
			}
		}

		// if this is not a "simple" node, throw an exception
		throw new IllegalArgumentException(node.getNodeName());
	}

	/**
	 * Return the text content of the specified child node.
	 * The child node must exist (or you will get a {@link NullPointerException}).
	 * <p>
	 * For example, given the following XML:
	 * <pre>
	 * &lt;parent>
	 *     &lt;child>Charlie&lt;/child>
	 * &lt;/parent>
	 * </pre>
	 * <code>XML.getChildTextContent(parentNode, "child")</code>
	 * will return <code>"Charlie"</code>.
	 */
	public static String getChildTextContent(Node parent, String childName) {
		return getTextContent(getChild(parent, childName));
	}

	/**
	 * Return the text content of the specified child node.
	 * If the child node does not exist, return the specified default value.
	 */
	public static String getChildTextContent(Node parent, String childName, String defaultValue) {
		Node child = getChild(parent, childName);
		return (child == null) ? defaultValue : getTextContent(child);
	}

	/**
	 * Return the <code>int</code> content of the specified child node.
	 * The child node must exist (or you will get a {@link NullPointerException}).
	 */
	public static int getChildIntContent(Node parent, String childName) {
		return convertToInt(getTextContent(getChild(parent, childName)));
	}

	/**
	 * Return the <code>int</code> content of the specified child node.
	 * If the child node does not exist, return the specified default value.
	 */
	public static int childIntContent(Node parent, String childName, int defaultValue) {
		Node child = getChild(parent, childName);
		return (child == null) ? defaultValue : convertToInt(getTextContent(child));
	}

	/**
	 * Convert the specified string to an int.
	 */
	private static int convertToInt(String string) {
		return Integer.parseInt(string);
	}

	/**
	 * Return the <code>boolean</code> content of the specified child node.
	 * The child node must exist (or you will get a {@link NullPointerException}).
	 */
	public static boolean getChildBooleanContent(Node parent, String childName) {
		return convertToBoolean(getTextContent(getChild(parent, childName)));
	}

	/**
	 * Return the <code>boolean</code> content of the specified child node.
	 * If the child node does not exist, return the specified default value.
	 */
	public static boolean getChildBooleanContent(Node parent, String childName, boolean defaultValue) {
		Node child = getChild(parent, childName);
		return (child == null) ? defaultValue : convertToBoolean(getTextContent(child));
	}

	/**
	 * Convert the specified string to a <code>boolean</code>.
	 */
	private static boolean convertToBoolean(String string) {
		String s = string.toLowerCase();
		if (s.equals("t") || s.equals("true") || s.equals("1")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return true;
		}
		if (s.equals("f") || s.equals("false") || s.equals("0")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return false;
		}
		throw new IllegalArgumentException(string);
	}


	// ********** writing DOM **********

	/**
	 * Build and return a new document. Once the document has been
	 * built, it can be printed later by calling {@link #print(Document, File)}
	 * or {@link #print(Document, OutputStream)}.
	 */
	public static Document newDocument() {
		return getDocumentBuilder().newDocument();
	}

	/**
	 * Add a simple text node with the specified name and text
	 * to the specified parent node.
	 */
	public static void addSimpleTextNode(Node parent, String childName, String text) {
		Document document = parent.getOwnerDocument();
		Node child = document.createElement(childName);
		Node childTextNode = document.createTextNode(text);
		child.appendChild(childTextNode);
		parent.appendChild(child);
	}

	/**
	 * Add a simple text node with the specified name and text
	 * to the specified parent node. If the text equals the default
	 * value, do not add the simple text node at all.
	 */
	public static void addSimpleTextNode(Node parent, String childName, String text, String defaultValue) {
		if ( ! text.equals(defaultValue)) {
			addSimpleTextNode(parent, childName, text);
		}
	}

	/**
	 * Add a simple text node with the specified name and numeric text
	 * to the specified parent node.
	 */
	public static void addSimpleTextNode(Node parent, String childName, int text) {
		addSimpleTextNode(parent, childName, String.valueOf(text));
	}

	/**
	 * Add a simple text node with the specified name and numeric text
	 * to the specified parent node. If numeric text equals the default
	 * value, do not add the simple text node at all.
	 */
	public static void addSimpleTextNode(Node parent, String childName, int text, int defaultValue) {
		if (text != defaultValue) {
			addSimpleTextNode(parent, childName, text);
		}
	}

	/**
	 * Add a simple text node with the specified name and boolean text
	 * to the specified parent node.
	 */
	public static void addSimpleTextNode(Node parent, String childName, boolean text) {
		addSimpleTextNode(parent, childName, String.valueOf(text));
	}

	/**
	 * Add a simple text node with the specified name and boolean text
	 * to the specified parent node. If the boolean text equals the default
	 * value, do not add the simple text node at all.
	 */
	public static void addSimpleTextNode(Node parent, String childName, boolean text, boolean defaultValue) {
		if (text != defaultValue) {
			addSimpleTextNode(parent, childName, text);
		}
	}

	/**
	 * Add a list of simple text nodes with the specified name and text
	 * to the specified parent node's children node.
	 * <p>
	 * For example, the following call:
	 * <pre>
	 * XML.addSimpleTextNodes(parentNode, "children", "child", new String[] {"foo", "bar", "baz"})
	 * </pre>
	 * will generate the following XML:
	 * <pre>
	 * &lt;parent>
	 *     ...
	 *     &lt;children>
	 *         &lt;child>foo&lt;/child>
	 *         &lt;child>bar&lt;/child>
	 *         &lt;child>baz&lt;/child>
	 *     &lt;/children>
	 *     ...
	 * &lt;/parent>
	 * </pre>
	 */
	public static void addSimpleTextNodes(Node parent, String childrenName, String childName, String[] childrenTexts) {
		Node childrenNode = parent.getOwnerDocument().createElement(childrenName);
		parent.appendChild(childrenNode);
		int len = childrenTexts.length;
		for (int i = 0; i < len; i++) {
			addSimpleTextNode(childrenNode, childName, childrenTexts[i]);
		}
	}

	/**
	 * Print the specified source to the specified result.
	 */
	public static synchronized void print(Source source, Result result) {
		try {
			getTransformer().transform(source, result);
		} catch (TransformerException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Print the specified document to the specified output stream.
	 * Document#print(OutputStream outputStream)
	 */
	public static void print(Document document, OutputStream outputStream) {
		print(new DOMSource(document), new StreamResult(outputStream));
	}

	/**
	 * Print the previously built document to the specified file.
	 * Document#print(File file)
	 */
	public static void print(Document document, File file) {
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), 8192)) { // 8KB
			print(document, outputStream);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static synchronized Transformer getTransformer() {
		if (TRANSFORMER == null) {
			try {
				TRANSFORMER = getTransformerFactory().newTransformer();
			} catch (TransformerConfigurationException ex) {
				throw new RuntimeException(ex);
			}
			try {
				TRANSFORMER.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			} catch (IllegalArgumentException ex) {
				// ignore exception - the output will still be valid XML, it just won't be very user-friendly
			}
		}
		return TRANSFORMER;
	}

	/**
	 * See {@link javax.xml.transform.TransformerFactory#newInstance()}
	 * for documentation on how the implementation class is determined
	 */
	private static synchronized TransformerFactory getTransformerFactory() {
		if (TRANSFORMER_FACTORY == null) {
			TRANSFORMER_FACTORY = TransformerFactory.newInstance();
		}
		return TRANSFORMER_FACTORY;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private XMLTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
