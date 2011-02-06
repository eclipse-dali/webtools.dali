/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;

public class GenericJavaLobConverter
	extends AbstractJavaConverter
	implements JavaLobConverter
{
	protected final LobAnnotation lobAnnotation;

	public GenericJavaLobConverter(JavaAttributeMapping parent, LobAnnotation lobAnnotation) {
		super(parent);
		this.lobAnnotation = lobAnnotation;
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return LobConverter.class;
	}

	@Override
	protected String getAnnotationName() {
		return LobAnnotation.ANNOTATION_NAME;
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.lobAnnotation.getTextRange(astRoot);
	}
}
