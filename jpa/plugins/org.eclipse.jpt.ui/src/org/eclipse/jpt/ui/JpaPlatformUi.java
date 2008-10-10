/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import java.util.Iterator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality.  This is intended to work in conjunction with a core
 * JPA platform ({@link JpaPlatform}) implementation with the same ID.
 * <p>
 * Any implementation should be <i>stateless</i> in nature.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 *
 * @see the org.eclipse.jpt.ui.jpaPlatform extension point
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatformUi
{
	/**
	 * Return a *new* {@link JpaNavigatorProvider}, which determines
	 * Project Explorer content and look
	 */
	JpaNavigatorProvider buildNavigatorProvider();

	/**
	 * Return a *new* structure provider for the given JPA file
	 */
	// TODO - binary java type support
	JpaStructureProvider buildStructureProvider(JpaFile jpaFile);

	Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> defaultJavaAttributeMappingUiProviders();

	Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> defaultOrmAttributeMappingUiProviders();

	JpaDetailsProvider getDetailsProvider(JpaStructureNode contextNode);

	void generateDDL(JpaProject project, IStructuredSelection selection);

	void generateEntities(JpaProject project, IStructuredSelection selection);

	JpaUiFactory getJpaUiFactory();

	Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> javaAttributeMappingUiProviders();

	Iterator<TypeMappingUiProvider<? extends TypeMapping>> javaTypeMappingUiProviders();

	Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> ormAttributeMappingUiProviders();

	Iterator<TypeMappingUiProvider<? extends TypeMapping>> ormTypeMappingUiProviders();
}