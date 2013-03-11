/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkPersistentAttributeValidator;

/**
 * EclipseLink Java persistent attribute
 */
public class EclipseLinkSpecifiedJavaPersistentAttribute
	extends AbstractJavaPersistentAttribute
	implements EclipseLinkJavaPersistentAttribute
{

	public EclipseLinkSpecifiedJavaPersistentAttribute(PersistentType parent, JavaResourceField resourceField) {
		super(parent,resourceField);
	}

	public EclipseLinkSpecifiedJavaPersistentAttribute(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent, resourceGetter, resourceSetter);
	}

	public EclipseLinkSpecifiedJavaPersistentAttribute(PersistentType parent, Accessor accessor) {
		super(parent, accessor);
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
		return ObjectTools.equals(key, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) &&
			ObjectTools.equals(this.getDefaultMappingKey(), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}


	// ********** type **********

	/**
	 * Return whether the attribute's type is a subclass of
	 * <code>java.util.Date</code> or <code>java.util.Calendar</code>.
	 */
	public boolean typeIsDateOrCalendar() {
		return this.getResourceAttribute().getTypeBinding().isSubTypeOf(DATE_TYPE_NAME)
				|| this.getResourceAttribute().getTypeBinding().isSubTypeOf(CALENDAR_TYPE_NAME);
	}

	public boolean typeIsSerializable() {
		return this.getResourceAttribute().getTypeBinding().isVariablePrimitive()
				|| this.getResourceAttribute().getTypeBinding().isSubTypeOf(JDTTools.SERIALIZABLE_CLASS_NAME);
	}

	public boolean typeIsValidForVariableOneToOne() {
		return this.getResourceAttribute().getTypeBinding().isInterface()
				&& this.interfaceIsValidForVariableOneToOne();
	}

	protected boolean interfaceIsValidForVariableOneToOne() {
		return ! this.interfaceIsInvalidForVariableOneToOne();
	}

	// TODO we could probably add more interfaces to this list...
	protected boolean interfaceIsInvalidForVariableOneToOne() {
		String interfaceName = this.getTypeName();
		return (interfaceName == null) ||
				interfaceName.equals("org.eclipse.persistence.indirection.ValueHolderInterface") || //$NON-NLS-1$
				this.typeIsContainer();
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttributeValidator() {
		return new EclipseLinkPersistentAttributeValidator(this);
	}
}
