/*******************************************************************************
 * Copyright (c) 20012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCurator;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.JaxbPackageItemContentProvider;

public class ELJaxbPackageContentProvider
		extends JaxbPackageItemContentProvider<ELJaxbPackage> {
	
	public ELJaxbPackageContentProvider(ELJaxbPackage jaxbPackage, Manager manager) {
		super(jaxbPackage, manager);
	}
	
	
	@Override
	public ELJaxbContextRoot getParent() {
		return (ELJaxbContextRoot) super.getParent();
	}
	
	@Override
	protected CollectionValueModel<JaxbContextNode> buildChildrenModel() {
		return CompositeCollectionValueModel.forModels(
				buildOxmFileChildrenModel(),
				buildJavaTypeChildrenModel());
	}
	
	protected CollectionValueModel<JaxbContextNode> buildOxmFileChildrenModel() {
		return new FilteringCollectionValueModel(
				new PropertyCollectionValueModelAdapter(
						new PropertyAspectAdapter<ELJaxbPackage, OxmFile>(
								ELJaxbPackage.OXM_FILE_PROPERTY, 
								(ELJaxbPackage) ELJaxbPackageContentProvider.this.item) {
							@Override
							protected OxmFile buildValue_() {
								return this.subject.getOxmFile();
							}
						}),
				PredicateTools.notNullPredicate());
	}
	
	protected CollectionValueModel<JaxbContextNode> buildJavaTypeChildrenModel() {
		return new ListCollectionValueModelAdapter<JaxbContextNode>(
				new ListCurator<ELJaxbContextRoot, JaxbContextNode>(getParent()) {
					@Override
					protected Iterator<JaxbContextNode> iteratorForRecord() {
						final ELJaxbContextRoot contextRoot = this.subject;
						return new SuperIterableWrapper<JaxbContextNode>(
								IterableTools.filter(
										this.subject.getJavaTypes(ELJaxbPackageContentProvider.this.item),
										new JavaTypeFilter(contextRoot))).iterator();
					}
				});
	}
	
	protected class JavaTypeFilter
			implements Predicate<JavaType> {
		
		private final ELJaxbContextRoot contextRoot;
		
		protected JavaTypeFilter(ELJaxbContextRoot contextRoot) {
			this.contextRoot = contextRoot;
		}
		
		public boolean evaluate(JavaType o) {
			String typeName = o.getTypeName().getFullyQualifiedName();
			// TODO xml-registry, xml-java-type-adapter
			JavaTypeMapping typeMapping = o.getMapping();
			if (typeMapping != null && contextRoot.getTypeMapping(typeName) == typeMapping) {
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
}
