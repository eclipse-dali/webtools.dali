/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.java;

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.ResourceUiDefinition;
import org.eclipse.jpt.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.ui.internal.details.java.AbstractJavaResourceUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.DefaultBasicMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.DefaultEmbeddedMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaBasicMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaEmbeddableUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaEmbeddedIdMappingUDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaEmbeddedMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaEntityUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaIdMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaManyToManyMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaManyToOneMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaMappedSuperclassUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaOneToManyMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaOneToOneMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaTransientMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.JavaVersionMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.java.NullJavaAttributeMappingUiDefinition;

public class Generic2_0JavaResourceUiDefinition extends AbstractJavaResourceUiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new Generic2_0JavaResourceUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * zero-argument constructor
	 */
	protected Generic2_0JavaResourceUiDefinition() {
		super();
	}
	
	@Override
	protected JavaUiFactory buildJavaUiFactory() {
		return new Generic2_0JavaUiFactory();
	}
	
	@Override
	protected void addSpecifiedAttributeMappingUiDefinitionsTo(List<JavaAttributeMappingUiDefinition<? extends AttributeMapping>> definitions) {
//		definitions.add(JavaElementCollectionMappingUiProvider.instance());
		definitions.add(JavaIdMappingUiDefinition.instance());
		definitions.add(JavaEmbeddedIdMappingUDefinition.instance());
		definitions.add(JavaBasicMappingUiDefinition.instance());
		definitions.add(JavaVersionMappingUiDefinition.instance());
		definitions.add(JavaManyToOneMappingUiDefinition.instance());
		definitions.add(JavaOneToManyMappingUiDefinition.instance());
		definitions.add(JavaOneToOneMappingUiDefinition.instance());
		definitions.add(JavaManyToManyMappingUiDefinition.instance());
		definitions.add(JavaEmbeddedMappingUiDefinition.instance());
		definitions.add(JavaTransientMappingUiDefinition.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiDefinitionsTo(List<DefaultJavaAttributeMappingUiDefinition<?>> definitions) {
		definitions.add(DefaultBasicMappingUiDefinition.instance());
		definitions.add(DefaultEmbeddedMappingUiDefinition.instance());
		definitions.add(NullJavaAttributeMappingUiDefinition.instance());
	}
	
	@Override
	protected void addSpecifiedTypeMappingUiDefinitionsTo(List<JavaTypeMappingUiDefinition<? extends TypeMapping>> definitions) {
		definitions.add(JavaEntityUiDefinition.instance());
		definitions.add(JavaMappedSuperclassUiDefinition.instance());
		definitions.add(JavaEmbeddableUiDefinition.instance());
	}
}
