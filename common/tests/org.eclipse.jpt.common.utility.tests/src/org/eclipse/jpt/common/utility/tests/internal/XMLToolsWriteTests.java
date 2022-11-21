/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.SystemTools;
import org.eclipse.jpt.common.utility.internal.XMLTools;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * these tests assume the XML will be formatted with appropriate
 * indentation?
 */
@SuppressWarnings("nls")
public class XMLToolsWriteTests
	extends TestCase
{
	private Document testDocument;
	private Node rootNode;
	private static final String CR = System.getProperty("line.separator");


	public XMLToolsWriteTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testDocument = XMLTools.newDocument();
		this.rootNode = this.testDocument.createElement("root-element");
		this.testDocument.appendChild(this.rootNode);
		XMLTools.addSimpleTextNode(this.rootNode, "element-0", "foo");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private void verifyTestDocumentString(String string) throws Exception {
		OutputStream stream = new ByteArrayOutputStream(2000);
		XMLTools.print(this.testDocument, stream);
		stream.close();
		StringBuffer sb = new StringBuffer(2000);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"");
		if (((SystemTools.jvmIsOracle() || SystemTools.jvmIsApple())
				&& SystemTools.javaSpecificationVersionIsGreaterThan("1.5"))
				|| SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("11")) {
			sb.append(" standalone=\"no\"");
		}
		sb.append("?>");
		if (SystemTools.osIsMac()) {
			sb.append(' ');
		}
		if (((SystemTools.jvmIsOracle() || SystemTools.jvmIsApple())
				&& SystemTools.javaSpecificationVersionIsGreaterThan("1.5"))
				|| SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("11")) {
			sb.append(CR);
		}
		sb.append("<root-element>");
		sb.append(CR);
		sb.append("<element-0>");
		sb.append("foo");
		sb.append("</element-0>");
		sb.append(CR);
		sb.append(string);
		sb.append("</root-element>");
		if (SystemTools.osIsMac()) {
			sb.append(' ');
		}
		if (((SystemTools.jvmIsOracle() || SystemTools.jvmIsApple())
				&& SystemTools.javaSpecificationVersionIsGreaterThan("1.5"))
				|| SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("11")) {
			sb.append(CR);
		}
		String expected = sb.toString();
		String actual = stream.toString();
		assertEquals(StringTools.compressWhitespace(expected), StringTools.compressWhitespace(actual));
	}

	public void testAddSimpleTextNode() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", "some text");
		this.verifyTestDocumentString("<element-1>some text</element-1>" + CR);
	}

	public void testAddSimpleTextNodeDefaultValue1() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", "some text", "some text");
		this.verifyTestDocumentString("");
	}

	public void testAddSimpleTextNodeDefaultValue2() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", "some text", "default text");
		this.verifyTestDocumentString("<element-1>some text</element-1>" + CR);
	}

	public void testAddSimpleTextNodeInt() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", 42);
		this.verifyTestDocumentString("<element-1>42</element-1>" + CR);
	}

	public void testAddSimpleTextNodeIntDefaultValue1() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", 42, 42);
		this.verifyTestDocumentString("");
	}

	public void testAddSimpleTextNodeIntDefaultValue2() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", 42, 43);
		this.verifyTestDocumentString("<element-1>42</element-1>" + CR);
	}

	public void testAddSimpleTextNodeBoolean() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", true);
		this.verifyTestDocumentString("<element-1>true</element-1>" + CR);
	}

	public void testAddSimpleTextNodeBooleanDefaultValue1() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", true, true);
		this.verifyTestDocumentString("");
	}

	public void testAddSimpleTextNodeBooleanDefaultValue2() throws Exception {
		XMLTools.addSimpleTextNode(this.rootNode, "element-1", false, true);
		this.verifyTestDocumentString("<element-1>false</element-1>" + CR);
	}

	public void testAddSimpleTextNodes() throws Exception {
		XMLTools.addSimpleTextNodes(this.rootNode, "element-1-collection", "element-1", new String[] {"text 1", "text 2", "text 3"});
		StringBuffer sb = new StringBuffer(2000);
		sb.append("<element-1-collection>");
		sb.append(CR);
		sb.append("<element-1>text 1</element-1>");
		sb.append(CR);
		sb.append("<element-1>text 2</element-1>");
		sb.append(CR);
		sb.append("<element-1>text 3</element-1>");
		sb.append(CR);
		sb.append("</element-1-collection>");
		sb.append(CR);
		this.verifyTestDocumentString(sb.toString());
	}
}
