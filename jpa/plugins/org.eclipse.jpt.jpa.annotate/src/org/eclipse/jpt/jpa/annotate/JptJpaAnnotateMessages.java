/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate;

import org.eclipse.osgi.util.NLS;

public class JptJpaAnnotateMessages 
{
	private static final String BUNDLE_NAME = "jpt_jpa_annotate"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaAnnotateMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String ANNOTATE_JAVA_CLASS;

	private JptJpaAnnotateMessages() {
		throw new UnsupportedOperationException();
	}
	
}
