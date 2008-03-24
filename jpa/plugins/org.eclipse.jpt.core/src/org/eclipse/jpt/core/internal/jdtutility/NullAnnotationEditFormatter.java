/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public final class NullAnnotationEditFormatter
	implements AnnotationEditFormatter
{

	private static final NullAnnotationEditFormatter INSTANCE = new NullAnnotationEditFormatter();

	/**
	 * Return the singleton.
	 */
	public static AnnotationEditFormatter instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NullAnnotationEditFormatter() {
		super();
	}

	public void format(IDocument doc, TextEdit editTree) throws MalformedTreeException, BadLocationException {
		// no formatting
	}

}
