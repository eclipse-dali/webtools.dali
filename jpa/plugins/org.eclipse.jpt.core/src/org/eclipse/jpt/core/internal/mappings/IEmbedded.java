/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IOverride.Owner;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IEmbedded</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEmbedded#getAttributeOverrides <em>Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEmbedded#getSpecifiedAttributeOverrides <em>Specified Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEmbedded#getDefaultAttributeOverrides <em>Default Attribute Overrides</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbedded()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IEmbedded extends IAttributeMapping
{
	/**
	 * Returns the value of the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbedded_AttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<IAttributeOverride> getAttributeOverrides();

	/**
	 * Returns the value of the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbedded_SpecifiedAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	EList<IAttributeOverride> getSpecifiedAttributeOverrides();

	/**
	 * Returns the value of the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbedded_DefaultAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	EList<IAttributeOverride> getDefaultAttributeOverrides();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	IEmbeddable embeddable();

	IAttributeOverride createAttributeOverride(int index);

	Iterator<String> allOverridableAttributeNames();

	boolean containsAttributeOverride(String name);

	boolean containsSpecifiedAttributeOverride(String name);


	class AttributeOverrideOwner implements Owner
	{
		private IEmbedded embedded;

		public AttributeOverrideOwner(IEmbedded embedded) {
			this.embedded = embedded;
		}

		public ITypeMapping getTypeMapping() {
			return this.embedded.typeMapping();
		}

		public IAttributeMapping attributeMapping(String attributeName) {
			return (IAttributeMapping) columnMapping(attributeName);
		}

		private IColumnMapping columnMapping(String name) {
			IEmbeddable embeddable = this.embedded.embeddable();
			if (embeddable != null) {
				for (Iterator<IPersistentAttribute> stream = embeddable.getPersistentType().allAttributes(); stream.hasNext();) {
					IPersistentAttribute persAttribute = stream.next();
					if (persAttribute.getName().equals(name)) {
						if (persAttribute.getMapping() instanceof IColumnMapping) {
							return (IColumnMapping) persAttribute.getMapping();
						}
					}
				}
			}
			return null;
		}

		public boolean isVirtual(IOverride override) {
			return embedded.getDefaultAttributeOverrides().contains(override);
		}

		public ITextRange getTextRange() {
			return embedded.getTextRange();
		}
	}
}