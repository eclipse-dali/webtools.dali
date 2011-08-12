/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;

public class GenericJavaLobConverter
	extends AbstractJavaConverter
	implements JavaLobConverter
{
	protected final LobAnnotation lobAnnotation;

	public GenericJavaLobConverter(JavaAttributeMapping parent, LobAnnotation lobAnnotation, JavaConverter.Owner owner) {
		super(parent, owner);
		this.lobAnnotation = lobAnnotation;
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return LobConverter.class;
	}

	public LobAnnotation getConverterAnnotation() {
		return this.lobAnnotation;
	}
}
