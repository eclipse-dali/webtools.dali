/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.io.StringReader;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.XMLTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@SuppressWarnings("nls")
public class XMLToolsReadTests
	extends TestCase
{
	private Document testDocument;
	private Node rootNode;


	public XMLToolsReadTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testDocument = this.buildTestDocument();
		this.rootNode = XMLTools.getChild(this.testDocument, "root-element");
	}

	private Document buildTestDocument() {
		return XMLTools.parse(new StringReader(this.buildTestDocumentString()));
	}

	private String buildTestDocumentString() {
		StringBuffer sb = new StringBuffer(2000);
		sb.append("<?xml version = '1.0' encoding = 'UTF-8'?>");
		sb.append("<root-element>");
		sb.append(		"<element-0>");
		sb.append(			"<element-0-text-1>some text</element-0-text-1>");
		sb.append(			"<element-0-text-2></element-0-text-2>");
		sb.append(			"<element-0-text-3/>");
		sb.append(			"<element-0-non-text>");
		sb.append(				"<element-0-non-text-child>");
		sb.append(				"</element-0-non-text-child>");
		sb.append(			"</element-0-non-text>");
		sb.append(		"</element-0>");
		sb.append(		"<element-1>");
		sb.append(			"<element-1-int>42</element-1-int>");
		sb.append(			"<element-1-boolean-true-1>true</element-1-boolean-true-1>");
		sb.append(			"<element-1-boolean-true-2>T</element-1-boolean-true-2>");
		sb.append(			"<element-1-boolean-true-3>1</element-1-boolean-true-3>");
		sb.append(			"<element-1-boolean-false-1>false</element-1-boolean-false-1>");
		sb.append(			"<element-1-boolean-false-2>F</element-1-boolean-false-2>");
		sb.append(			"<element-1-boolean-false-3>0</element-1-boolean-false-3>");
		sb.append(		"</element-1>");
		sb.append(		"<element-2>");
		sb.append(			"<element-2.0>");
		sb.append(			"</element-2.0>");
		sb.append(			"<element-2.0>");
		sb.append(			"</element-2.0>");
		sb.append(			"<element-2.0>");
		sb.append(			"</element-2.0>");
		sb.append(		"</element-2>");
		sb.append(		"<element-3>element 3 contents</element-3>");
		sb.append("</root-element>");
		return sb.toString();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetChild() {
		assertEquals("element-1", XMLTools.getChild(this.rootNode, "element-1").getNodeName());
		assertEquals("element-3", XMLTools.getChild(this.rootNode, "element-3").getNodeName());
		assertEquals(null, XMLTools.getChild(this.rootNode, "element-1x"));
	}

	public void testGetChildren() {
		Iterable<Node> children = XMLTools.getChildren(this.rootNode);
		assertEquals(4, IterableTools.size(children));
		for (int i = 1; i < 4; i++) {
			assertEquals("element-" + i, IterableTools.get(children, i).getNodeName());
		}
	}

	public void testGetChildrenNamed() {
		Node element2Node = XMLTools.getChild(this.rootNode, "element-2");
		Iterable<Node> children = XMLTools.getChildren(element2Node, "element-2.0");
		assertEquals(3, IterableTools.size(children));
		for (int i = 0; i < 3; i++) {
			assertEquals("element-2.0", IterableTools.get(children, i).getNodeName());
		}
	}

	public void testGetTextContent() {
		Node node = XMLTools.getChild(this.rootNode, "element-0");
		Node childNode = XMLTools.getChild(node, "element-0-text-1");
		assertEquals("some text", XMLTools.getTextContent(childNode));

		childNode = XMLTools.getChild(node, "element-0-text-2");
		assertEquals("", XMLTools.getTextContent(childNode));

		childNode = XMLTools.getChild(node, "element-0-text-3");
		assertEquals("", XMLTools.getTextContent(childNode));

		childNode = XMLTools.getChild(node, "element-0-non-text");
		boolean exCaught = false;
		try {
			String text = XMLTools.getTextContent(childNode);
			text = text.toString();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testGetChildTextContent() {
		assertEquals("element 3 contents", XMLTools.getChildTextContent(this.rootNode, "element-3"));
	}

	public void testGetChildTextContentDefaultValue() {
		assertEquals("element 3 contents", XMLTools.getChildTextContent(this.rootNode, "element-3", "default value 3"));
		assertEquals("default value 4", XMLTools.getChildTextContent(this.rootNode, "element-4", "default value 4"));
	}

	public void testGetChildIntContent() {
		Node node = XMLTools.getChild(this.rootNode, "element-1");
		assertEquals(42, XMLTools.getChildIntContent(node, "element-1-int"));
	}

	public void testGetChildIntContentDefaultValue() {
		Node node = XMLTools.getChild(this.rootNode, "element-1");
		assertEquals(42, XMLTools.childIntContent(node, "element-1-int", 99));
		assertEquals(99, XMLTools.childIntContent(node, "element-1-int-x", 99));
	}

	public void testGetChildBooleanContent() {
		Node node = XMLTools.getChild(this.rootNode, "element-1");
		assertTrue(XMLTools.getChildBooleanContent(node, "element-1-boolean-true-1"));
		assertTrue(XMLTools.getChildBooleanContent(node, "element-1-boolean-true-2"));
		assertTrue(XMLTools.getChildBooleanContent(node, "element-1-boolean-true-3"));

		assertFalse(XMLTools.getChildBooleanContent(node, "element-1-boolean-false-1"));
		assertFalse(XMLTools.getChildBooleanContent(node, "element-1-boolean-false-2"));
		assertFalse(XMLTools.getChildBooleanContent(node, "element-1-boolean-false-3"));
	}

	public void testGetChildBooleanContentDefaultValue() {
		Node node = XMLTools.getChild(this.rootNode, "element-1");
		assertTrue(XMLTools.getChildBooleanContent(node, "element-1-boolean-true-1", false));
		assertTrue(XMLTools.getChildBooleanContent(node, "element-1-boolean-true-2", false));
		assertTrue(XMLTools.getChildBooleanContent(node, "element-1-boolean-true-3", false));
		assertFalse(XMLTools.getChildBooleanContent(node, "element-1-boolean-true-bogus", false));

		assertFalse(XMLTools.getChildBooleanContent(node, "element-1-boolean-false-1", true));
		assertFalse(XMLTools.getChildBooleanContent(node, "element-1-boolean-false-2", true));
		assertFalse(XMLTools.getChildBooleanContent(node, "element-1-boolean-false-3", true));
		assertTrue(XMLTools.getChildBooleanContent(node, "element-1-boolean-false-bogus", true));
	}
}
