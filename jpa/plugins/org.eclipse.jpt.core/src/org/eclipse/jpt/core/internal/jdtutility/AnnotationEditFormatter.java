/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
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
 * This will apply TextEdit's to a doc and do some extra work to cleanup some
 * of the nasty Eclipse annotation formatting (e.g. arrays of annotations).
 */
public class AnnotationEditFormatter {
	
	protected IDocument doc;

	public AnnotationEditFormatter(IDocument doc) {
		this.doc = doc;
	}
	
	/**
	 * Apply the edits to our doc doing extra formatting as needed. 
	 */
	public void apply(TextEdit edits) throws MalformedTreeException, 
			BadLocationException {
		edits.apply(doc, TextEdit.UPDATE_REGIONS);
		MultiTextEdit extra = new MultiTextEdit();
		TextEdit[] all = edits.getChildren();
		for (int i = 0; i < all.length; i++) {
			if (all[i] instanceof InsertEdit) {
				InsertEdit a = (InsertEdit)all[i];
				if (i + 1 < all.length && all[i + 1] instanceof InsertEdit) {
					InsertEdit b = (InsertEdit)all[i + 1];
					if (" ".equals(b.getText()) && isAnnotation(a)) {
						// Annotation being inserted before something on the same line.
						// Insert a newline and indent after the annotation.
						ReplaceEdit re = new ReplaceEdit(b.getOffset(), 1, 
								getNewlineAndIdent(b.getOffset()));
						extra.addChild(re);
						i++;
						continue;
					}
					if (", ".equals(a.getText()) && isAnnotation(b)) {
						// Annotation being inserted in an array initializer on the
						// same line as the previous array element.
						// Insert a newline and indent before the annotation.
						ReplaceEdit re = new ReplaceEdit(a.getOffset() + 1, 1, 
								getNewlineAndIdent(a.getOffset()));
						extra.addChild(re);
						i++;
						continue;
					}
				}
				if (formatArrayInit(a, extra)) {
					continue;
				}
			}
		}
		extra.apply(doc, 0);
	}
	
	/**
	 * Create a String containing a line delimeter and indenting characters 
	 * matching the indent level of the line containing character offset.
	 */
	protected String getNewlineAndIdent(int offset) throws BadLocationException {
		int line = doc.getLineOfOffset(offset);
		offset = doc.getLineOffset(line);
		StringBuffer s = new StringBuffer();
		s.append(doc.getLineDelimiter(line));
		for (;;) {
			char c = doc.getChar(offset++);
			if (c == ' ' || c == '\t') {
				s.append(c);
			} else {
				break;
			}
		}
		return s.toString();
	}
	
	/**
	 * Is e inserting an annotation? 
	 */
	protected boolean isAnnotation(InsertEdit e) {
		String s = e.getText();
		return s.length() > 1 && s.charAt(0) == '@';
	}

	/**
	 * If a is inserting an annotation containing an array of annotations as
	 * its value then format them nicely and return true.
	 */
	protected boolean formatArrayInit(InsertEdit a, MultiTextEdit extra) 
			throws BadLocationException {
		String s = a.getText();
		int n = s.length();
		if (n < 6 || s.charAt(0) != '@') {
			return false;
		}
		int pos;
		for (pos = 1; pos < n && s.charAt(pos++) != '('; ) {/* just move 'pos' */}
		if (pos == n) {
			return false;
		}
		for (; pos < n; ) {
			char c = s.charAt(pos++);
			if (c == '{') {
				break;
			}
			if (c != ' ') {
				return false;
			}
		}
		if (pos == n) {
			return false;
		}
		// now look for @ not inside parenthesis and put in 
		// line delimeter and indent string before each
		int offset = a.getOffset();
		String indent = null;
		int parenCount = 0;
		for (; pos < n; pos++) {
			char c = s.charAt(pos);
			switch (c) {
			case '(':
				++parenCount;
				break;
			case ')':
				--parenCount;
				break;
			case '@':
				if (parenCount == 0) {
					if (indent == null) {
						indent = getNewlineAndIdent(offset) + "\t";
					}
					InsertEdit ie = new InsertEdit(offset + pos, indent);
					extra.addChild(ie);
				}
			}
		}
		return indent != null;
	}
	
}
