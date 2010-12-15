/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.JDTFieldAttribute;
import org.eclipse.jpt.core.utility.jdt.FieldAttribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;

/**
 * Java source field
 */
final class SourceField
	extends SourceAttribute<FieldAttribute>
	implements JavaResourceField
{

	/**
	 * construct field attribute
	 */
	static JavaResourceField newInstance(
			JavaResourceType parent,
			Type declaringType,
			String name,
			int occurrence,
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			CompilationUnit astRoot) {
		FieldAttribute field = new JDTFieldAttribute(
				declaringType,
				name,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceField jrpa = new SourceField(parent, field);
		jrpa.initialize(astRoot);
		return jrpa;
	}

	private SourceField(JavaResourceType parent, FieldAttribute field){
		super(parent, field);
	}


	// ******** SourceAttribute implementation ********

	@Override
	protected String getModifiersProperty() {
		return MODIFIERS_PROPERTY;
	}

	@Override
	protected String getTypeNameProperty() {
		return TYPE_NAME_PROPERTY;
	}

	@Override
	protected String getTypeIsInterfaceProperty() {
		return TYPE_IS_INTERFACE_PROPERTY;
	}

	@Override
	protected String getTypeIsEnumProperty() {
		return TYPE_IS_ENUM_PROPERTY;
	}

	@Override
	protected String getTypeSuperclassNamesProperty() {
		return TYPE_SUPERCLASS_NAMES_LIST;
	}

	@Override
	protected String getTypeInterfaceNamesProperty() {
		return TYPE_INTERFACE_NAMES_COLLECTION;
	}

	@Override
	protected String getTypeTypeArgumentNamesProperty() {
		return TYPE_TYPE_ARGUMENT_NAMES_LIST;
	}
	


}
