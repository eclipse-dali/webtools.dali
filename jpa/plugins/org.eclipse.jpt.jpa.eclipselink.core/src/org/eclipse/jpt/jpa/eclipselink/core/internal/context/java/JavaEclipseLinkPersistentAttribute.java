/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v1_1.context.EclipseLinkPersistentAttributeValidator;

/**
 * EclipseLink Java persistent attribute
 */
public class JavaEclipseLinkPersistentAttribute
	extends AbstractJavaPersistentAttribute
{
	public JavaEclipseLinkPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		super(parent, jrpa);
	}

	// ********** mapping **********

	@Override
	protected JavaAttributeMappingDefinition getSpecifiedMappingDefinition(String key) {
		if (this.specifiedKeyIsInvalid(key)) {
			throw new IllegalArgumentException("invalid mapping: " + key); //$NON-NLS-1$
		}
		return super.getSpecifiedMappingDefinition(key);
	}

	/**
	 * EclipseLink does not support setting an attribute with a default 1:1
	 * mapping to a specified mapping of ID; because the resulting ID annotation
	 * indicates a primary key derived from the 1:1 mapping, <em>not</em> an ID
	 * mapping.
	 */
	protected boolean specifiedKeyIsInvalid(String key) {
		return Tools.valuesAreEqual(key, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) &&
			Tools.valuesAreEqual(this.getDefaultMappingKey(), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}


	// ********** type **********

	/**
	 * Return whether the attribute's type is a subclass of
	 * <code>java.util.Date</code> or <code>java.util.Calendar</code>.
	 */
	public boolean typeIsDateOrCalendar() {
		return this.resourcePersistentAttribute.typeIsSubTypeOf(DATE_TYPE_NAME)
				|| this.resourcePersistentAttribute.typeIsSubTypeOf(CALENDAR_TYPE_NAME);
	}
	protected static final String DATE_TYPE_NAME = java.util.Date.class.getName();
	protected static final String CALENDAR_TYPE_NAME = java.util.Calendar.class.getName();

	public boolean typeIsSerializable() {
		return this.resourcePersistentAttribute.typeIsVariablePrimitive()
				|| this.resourcePersistentAttribute.typeIsSubTypeOf(SERIALIZABLE_TYPE_NAME);
	}

	public boolean typeIsValidForVariableOneToOne() {
		return this.resourcePersistentAttribute.typeIsInterface()
				&& this.interfaceIsValidForVariableOneToOne(getTypeName());
	}

	protected boolean interfaceIsValidForVariableOneToOne(String interfaceName) {
		return ! this.interfaceIsInvalidForVariableOneToOne(interfaceName);
	}

	// TODO we could probably add more interfaces to this list...
	protected boolean interfaceIsInvalidForVariableOneToOne(String interfaceName) {
		return (interfaceName == null) ||
				this.typeIsContainer(interfaceName) ||
				interfaceName.equals("org.eclipse.persistence.indirection.ValueHolderInterface"); //$NON-NLS-1$
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator(CompilationUnit astRoot) {
		return new EclipseLinkPersistentAttributeValidator(this, this, buildTextRangeResolver(astRoot));
	}
}
