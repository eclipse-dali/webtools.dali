/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkPersistentAttributeValidator;

/**
 * EclipseLink Java persistent attribute
 */
public class JavaEclipseLinkPersistentAttribute
	extends AbstractJavaPersistentAttribute
	implements JavaPersistentAttribute2_0
{
	public JavaEclipseLinkPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		super(parent, jrpa);
	}


	// ********** AccessHolder implementation **********

	public AccessType getSpecifiedAccess() {
		return null;
	}

	public void setSpecifiedAccess(AccessType specifiedAccess) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return whether the specified type is a subclass of java.util.Date or java.util.Calendar.
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
