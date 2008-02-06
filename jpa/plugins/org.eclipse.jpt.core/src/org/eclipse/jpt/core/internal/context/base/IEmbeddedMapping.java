/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;

public interface IEmbeddedMapping extends IAttributeMapping, IOverride.Owner
{
	// **************** attribute overrides **************************************

	/**
	 * Return a list iterator of the attribute overrides whether specified or default.
	 * This will not be null.
	 */
	<T extends IAttributeOverride> ListIterator<T> attributeOverrides();
	
	/**
	 * Return the number of attribute overrides, both specified and default.
	 */
	int attributeOverridesSize();

	/**
	 * Return a list iterator of the specified attribute overrides.
	 * This will not be null.
	 */
	<T extends IAttributeOverride> ListIterator<T> specifiedAttributeOverrides();
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverridesList";
	
	/**
	 * Return the number of specified attribute overrides.
	 */
	int specifiedAttributeOverridesSize();

	/**
	 * Return a list iterator of the default attribute overrides.
	 * This will not be null.
	 */
	<T extends IAttributeOverride> ListIterator<T> defaultAttributeOverrides();
		String DEFAULT_ATTRIBUTE_OVERRIDES_LIST = "defaultAttributeOverridesList";

	/**
	 * Return the number of default attribute overrides.
	 */
	int defaultAttributeOverridesSize();

	/**
	 * Add a specified attribute override to the entity return the object 
	 * representing it.
	 */
	IAttributeOverride addSpecifiedAttributeOverride(int index);
	
	/**
	 * Remove the specified attribute override from the entity.
	 */
	void removeSpecifiedAttributeOverride(int index);
	
	/**
	 * Remove the specified attribute override at the index from the entity.
	 */
	void removeSpecifiedAttributeOverride(IAttributeOverride attributeOverride);
	
	/**
	 * Move the specified attribute override from the source index to the target index.
	 */
	void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex);

		
//	IEmbeddable embeddable();
//
//	IAttributeOverride createAttributeOverride(int index);
//
//	Iterator<String> allOverridableAttributeNames();
//
//	IAttributeOverride attributeOverrideNamed(String name);
//
//	boolean containsAttributeOverride(String name);
//
//	boolean containsSpecifiedAttributeOverride(String name);
//
//
//	class AttributeOverrideOwner implements Owner
//	{
//		private IEmbedded embedded;
//
//		public AttributeOverrideOwner(IEmbedded embedded) {
//			this.embedded = embedded;
//		}
//
//		public ITypeMapping getTypeMapping() {
//			return this.embedded.typeMapping();
//		}
//
//		public IAttributeMapping attributeMapping(String attributeName) {
//			return (IAttributeMapping) columnMapping(attributeName);
//		}
//
//		private IColumnMapping columnMapping(String name) {
//			IEmbeddable embeddable = this.embedded.embeddable();
//			if (embeddable != null) {
//				for (Iterator<IPersistentAttribute> stream = embeddable.getPersistentType().allAttributes(); stream.hasNext();) {
//					IPersistentAttribute persAttribute = stream.next();
//					if (persAttribute.getName().equals(name)) {
//						if (persAttribute.getMapping() instanceof IColumnMapping) {
//							return (IColumnMapping) persAttribute.getMapping();
//						}
//					}
//				}
//			}
//			return null;
//		}
//
//		public boolean isVirtual(IOverride override) {
//			return embedded.getDefaultAttributeOverrides().contains(override);
//		}
//
//		public ITextRange validationTextRange() {
//			return embedded.validationTextRange();
//		}
//	}
}