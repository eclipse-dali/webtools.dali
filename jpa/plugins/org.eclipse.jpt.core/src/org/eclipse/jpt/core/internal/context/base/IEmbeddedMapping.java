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
	<T extends IAttributeOverride> ListIterator<T> attributeOverrides();
	<T extends IAttributeOverride> ListIterator<T> specifiedAttributeOverrides();
	<T extends IAttributeOverride> ListIterator<T> defaultAttributeOverrides();
	int specifiedAttributeOverridesSize();
	IAttributeOverride addSpecifiedAttributeOverride(int index);
	void removeSpecifiedAttributeOverride(int index);
	void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex);
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverridesList";
		String DEFAULT_ATTRIBUTE_OVERRIDES_LIST = "defaultAttributeOverridesList";

		
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