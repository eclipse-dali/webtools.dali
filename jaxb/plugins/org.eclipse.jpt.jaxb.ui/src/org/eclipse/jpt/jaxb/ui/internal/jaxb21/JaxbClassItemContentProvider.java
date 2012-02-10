/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;


public class JaxbClassItemContentProvider
	extends AbstractItemTreeContentProvider<JaxbClass, JaxbPersistentAttribute>
{
	
	public JaxbClassItemContentProvider(JaxbClass jaxbClass, Manager manager) {
		super(jaxbClass, manager);
	}
	
	
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) this.item.getParent();
	}
	
	@Override
	protected CollectionValueModel<JaxbPersistentAttribute> buildChildrenModel() {
		PropertyValueModel<JaxbClassMapping> mappingModel = buildMappingModel();
		return new CompositeCollectionValueModel<CollectionValueModel<JaxbPersistentAttribute>, JaxbPersistentAttribute>(
				buildAttributesModel(mappingModel),
				buildIncludedAttributesModel(mappingModel));
	}
	
	protected PropertyValueModel<JaxbClassMapping> buildMappingModel() {
		return new PropertyAspectAdapter<JaxbClass, JaxbClassMapping>(JaxbClass.MAPPING_PROPERTY, this.item) {
			@Override
			protected JaxbClassMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}
	
	protected CollectionValueModel<JaxbPersistentAttribute> buildAttributesModel(
			PropertyValueModel<JaxbClassMapping> mappingModel) {
		return new CollectionAspectAdapter<JaxbClassMapping, JaxbPersistentAttribute>(
				mappingModel, JaxbClassMapping.ATTRIBUTES_COLLECTION) {
			@Override
			protected Iterable<JaxbPersistentAttribute> getIterable() {
				return this.subject.getAttributes();
			}
		};
	}

	protected CollectionValueModel<JaxbPersistentAttribute> buildIncludedAttributesModel(
			PropertyValueModel<JaxbClassMapping> mappingModel) {
		return new CollectionAspectAdapter<JaxbClassMapping, JaxbPersistentAttribute>(
				mappingModel, JaxbClassMapping.INCLUDED_ATTRIBUTES_COLLECTION) {
			@Override
			protected Iterable<JaxbPersistentAttribute> getIterable() {
				return this.subject.getIncludedAttributes();
			}
		};
	}
}
