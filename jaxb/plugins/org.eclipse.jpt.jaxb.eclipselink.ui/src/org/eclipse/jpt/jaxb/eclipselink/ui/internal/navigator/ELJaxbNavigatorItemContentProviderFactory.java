/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import java.util.Iterator;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCurator;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlEnum;
import org.eclipse.jpt.jaxb.ui.internal.AbstractNavigatorItemContentProviderFactory;

public class ELJaxbNavigatorItemContentProviderFactory
	extends AbstractNavigatorItemContentProviderFactory
{
	private static ELJaxbNavigatorItemContentProviderFactory INSTANCE 
			= new ELJaxbNavigatorItemContentProviderFactory();
	
	
	public static ELJaxbNavigatorItemContentProviderFactory instance() {
		return INSTANCE;
	}
	
	
	private ELJaxbNavigatorItemContentProviderFactory() {
		super();
	}
	
	@Override
	public ItemStructuredContentProvider buildProvider(Object input, Manager manager) {
		if (input instanceof OxmFile) {
			return this.buildItemStructuredContentProvider(input, this.buildOxmFileChildrenModel((OxmFile) input), manager);
		}
		if (input instanceof OxmJavaType) {
			return this.buildItemStructuredContentProvider(input, this.buildOxmJavaTypeChildrenModel((OxmJavaType) input), manager);
		}
		return super.buildProvider(input, manager);
	}
	
	@Override
	public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
		if (item instanceof OxmFile) {
			return this.buildItemTreeContentProvider(item, parent, this.buildOxmFileChildrenModel((OxmFile) item), manager);
		}
		if (item instanceof OxmJavaType) {
			return this.buildItemTreeContentProvider(item, parent, this.buildOxmJavaTypeChildrenModel((OxmJavaType) item), manager);
		}
		return super.buildProvider(item, parent, manager);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected CollectionValueModel<JaxbContextNode> buildJaxbPackageChildrenModel(JaxbPackage jaxbPackage) {
		return CompositeCollectionValueModel.forModels(
				buildOxmFileChildrenModel((ELJaxbPackage) jaxbPackage),
				buildJavaTypeChildrenModel((ELJaxbPackage) jaxbPackage)
			);
	}
	
	protected CollectionValueModel<JaxbContextNode> buildOxmFileChildrenModel(final ELJaxbPackage jaxbPackage) {
		return new FilteringCollectionValueModel<JaxbContextNode>(
				new PropertyCollectionValueModelAdapter<JaxbContextNode>(
						PropertyValueModelTools.modelAspectAdapter(
								jaxbPackage,
								ELJaxbPackage.OXM_FILE_PROPERTY,
								ELJaxbPackage.OXM_FILE_TRANSFORMER
							)),
				PredicateTools.<JaxbContextNode>isNotNull());
	}
	
	protected CollectionValueModel<JaxbContextNode> buildJavaTypeChildrenModel(final ELJaxbPackage jaxbPackage) {
		return new ListCollectionValueModelAdapter<JaxbContextNode>(
				new ListCurator<JaxbContextRoot, JaxbContextNode>(jaxbPackage.getContextRoot()) {
					@Override
					protected Iterator<JaxbContextNode> iteratorForRecord() {
						return new SuperIterableWrapper<JaxbContextNode>(
								IterableTools.filter(
										this.subject.getJavaTypes(jaxbPackage),
										new IsJavaType(this.subject))).iterator();
					}
				});
	}
	
	public static class IsJavaType
			extends CriterionPredicate<JavaType, JaxbContextRoot> {
		
		public IsJavaType(JaxbContextRoot contextRoot) {
			super(contextRoot);
		}
		
		public boolean evaluate(JavaType o) {
			String typeName = o.getTypeName().getFullyQualifiedName();
			// TODO xml-registry, xml-java-type-adapter
			JavaTypeMapping typeMapping = o.getMapping();
			if (typeMapping != null && this.criterion.getTypeMapping(typeName) == typeMapping) {
				return true;
			}
			if (o.getXmlJavaTypeAdapter() != null) {
				return true;
			}
			if (o.getKind() == TypeKind.CLASS && ((JavaClass) o).getXmlRegistry() != null) {
				return true;
			}
			return false;
		}
	}


	// ********** oxm file **********

	@SuppressWarnings("unchecked")
	protected CollectionValueModel<JaxbContextNode> buildOxmFileChildrenModel(OxmFile oxmFile) {
		return new ListCollectionValueModelAdapter<JaxbContextNode>(
				CompositeListValueModel.forModels(
						new ListAspectAdapter<OxmXmlBindings, OxmXmlEnum>(buildXmlBindingsModel(oxmFile), OxmXmlBindings.XML_ENUMS_LIST) {
							@Override
							protected ListIterable<OxmXmlEnum> getListIterable() {
								return this.subject.getXmlEnums();
							}
							@Override
							public int size() {
								return this.subject.getXmlEnumsSize();
							}
						},
						new ListAspectAdapter<OxmXmlBindings, OxmJavaType>(buildXmlBindingsModel(oxmFile), OxmXmlBindings.JAVA_TYPES_LIST) {
							@Override
							protected ListIterable<OxmJavaType> getListIterable() {
								return this.subject.getJavaTypes();
							}
							@Override
							protected int size_() {
								return this.subject.getJavaTypesSize();
							}
						}));
	}
	
	protected PropertyValueModel<OxmXmlBindings> buildXmlBindingsModel(OxmFile oxmFile) {
		return PropertyValueModelTools.modelAspectAdapter(
				oxmFile,
				OxmFile.XML_BINDINGS_PROPERTY,
				OxmFile.XML_BINDINGS_TRANSFORMER
			);
	}


	// ********** oxm Java type **********

	public CollectionValueModel<?> buildOxmJavaTypeChildrenModel(OxmJavaType oxmJavaType) {
		return new ListCollectionValueModelAdapter<OxmJavaAttribute>(
				new ItemPropertyListValueModelAdapter<OxmJavaAttribute>(
						new ListAspectAdapter<OxmJavaType, OxmJavaAttribute>(OxmJavaType.SPECIFIED_ATTRIBUTES_LIST, oxmJavaType) {
							@Override
							protected ListIterable<OxmJavaAttribute> getListIterable() {
								return this.subject.getSpecifiedAttributes();
							}
							@Override
							protected int size_() {
								return this.subject.getSpecifiedAttributesSize();
							}
						}));
	}
}
