/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

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

	public GenericJavaLobConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, LobAnnotation lobAnnotation) {
		super(parentAdapter);
		this.lobAnnotation = lobAnnotation;
	}


	// ********** misc **********

	public Class<LobConverter> getConverterType() {
		return LobConverter.class;
	}

	public LobAnnotation getConverterAnnotation() {
		return this.lobAnnotation;
	}
}
