/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * This implementation will clean up some of the nasty Eclipse annotation
 * formatting (or lack thereof); e.g. arrays of annotations.
 */
public final class DefaultAnnotationEditFormatter
	implements AnnotationEditFormatter
{
	private static DefaultAnnotationEditFormatter INSTANCE = new DefaultAnnotationEditFormatter();

	/**
	 * Return the singleton.
	 */
	public static DefaultAnnotationEditFormatter instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private DefaultAnnotationEditFormatter() {
		super();
	}

	/**
	 * TODO
	 */
	public void format(IDocument doc, TextEdit editTree) throws MalformedTreeException, BadLocationException {
		TextEdit[] edits = editTree.getChildren();
		int len = edits.length;
		if (len == 0) {
			return;
		}

		MultiTextEdit extraEdits = new MultiTextEdit();
		for (int i = 0; i < len; i++) {
			TextEdit edit1 = edits[i];
			if ( ! (edit1 instanceof InsertEdit)) {
				continue;  // if the edit is not an insert, skip to the next edit
			}
			InsertEdit insert1 = (InsertEdit) edit1;
			int j = i + 1;
			if (j < len) {
				TextEdit edit2 = edits[j];
				if (edit2 instanceof InsertEdit) {
					InsertEdit insert2 = (InsertEdit) edit2;
					String text1 = insert1.getText();
					String text2 = insert2.getText();
					int offset1 = insert1.getOffset();
					int offset2 = insert2.getOffset();
					if (this.stringIsAnnotation(text1) && text2.equals(" ")) {
						// an annotation was inserted before something on the same line;
						// replace the trailing space with a newline and appropriate indent
						extraEdits.addChild(new ReplaceEdit(offset2, 1, this.buildCR(doc, offset2)));
						i++;  // jump the index past 'edit2'
						continue;  // go to the next edit
					}
					int comma1Length = this.commaLength(text1);
					if ((comma1Length != 0) && this.stringIsAnnotation(text2)) {
						// an annotation was inserted in an array initializer on the
						// same line as the previous array element;
						// replace the preceding space with a newline and appropriate indent
						extraEdits.addChild(new ReplaceEdit(offset1 + comma1Length, text1.length() - comma1Length, this.buildCR(doc, offset1)));
						i++;  // jump the index past 'edit2'
						continue;  // go to the next edit
					}
				}
			}
			this.formatArrayInitializer(doc, insert1, extraEdits);
		}
		extraEdits.apply(doc, TextEdit.NONE);
	}

	/**
	 * If the insert edit is inserting an annotation containing an array of annotations as
	 * its value then format them nicely.
	 */
	private void formatArrayInitializer(IDocument doc, InsertEdit insertEdit, MultiTextEdit extraEdits) throws BadLocationException {
		String s = insertEdit.getText();
		if ( ! this.stringIsAnnotation(s)) {
			return;
		}
		int len = s.length();
		int pos = 1;  // skip '@'
		while (pos < len) {
			char c = s.charAt(pos);
			pos++;  // bump to just past first '('
			if (c == '(') {
				break;
			}
		}
		if (pos == len) {
			return;  // reached end of string
		}
		while (pos < len) {
			char c = s.charAt(pos);
			pos++;  // bump to just past first '{'
			if (c == '{') {
				break;
			}
			if (c != ' ') {
				return;
			}
		}
		if (pos == len) {
			return;  // reached end of string
		}
		// now look for '@' not inside parentheses and put in 
		// line delimeter and indent string before each
		int offset = insertEdit.getOffset();
		String indent = null;
		int parenDepth = 0;
		while (pos < len) {
			switch (s.charAt(pos)) {
				case '(' :
					parenDepth++;
					break;
				case ')' :
					parenDepth--;
					break;
				case '@' :
					if (parenDepth == 0) {
						if (indent == null) {
							indent = this.buildCR(doc, offset, "\t");  // TODO use tab preference?
						}
						extraEdits.addChild(new InsertEdit(offset + pos, indent));
					}
					break;
				case '}' :
					if (parenDepth == 0) {
						extraEdits.addChild(new InsertEdit(offset + pos, this.buildCR(doc, offset)));
					}
					break;
			}
			pos++;
		}
	}

	/**
	 * Build a string containing a line delimeter and indenting characters 
	 * matching the indent level of the line containing the character offset
	 * (i.e. the new line's indent matches the current line).
	 */
	private String buildCR(IDocument doc, int offset) throws BadLocationException {
		return this.buildCR(doc, offset, "");
	}

	private String buildCR(IDocument doc, int offset, String suffix) throws BadLocationException {
		int line = doc.getLineOfOffset(offset);
		StringBuffer sb = new StringBuffer();
		sb.append(doc.getLineDelimiter(line));  // use same CR as current line

		int o = doc.getLineOffset(line);  // match the whitespace of the current line
		char c = doc.getChar(o++);
		while ((c == ' ') || (c == '\t')) {
			sb.append(c);
			c = doc.getChar(o++);
		}
		sb.append(suffix);
		return sb.toString();
	}

	/**
	 * Return whether the specified string is an annotation.
	 */
	private boolean stringIsAnnotation(String string) {
		return (string.length() > 1) && string.charAt(0) == '@';
	}

	/**
	 * If the specified string is a single comma, possibly surrounded by
	 * spaces, return the length of the substring containing the
	 * initial spaces and the comma.
	 */
	private int commaLength(String string) {
		boolean comma = false;
		int len = string.length();
		int result = 0;
		for (int i = 0; i < len; i++) {
			switch (string.charAt(i)) {
				case ' ' :
					if ( ! comma) {
						result++;  // space preceding comma
					}
					break;
				case ',' :
					if (comma) {
						return 0;  // second comma!
					}
					comma = true;
					result++;
					break;
				default:
					return 0;  // non-comma, non-space char
			}
		}
		return result;
	}

}
