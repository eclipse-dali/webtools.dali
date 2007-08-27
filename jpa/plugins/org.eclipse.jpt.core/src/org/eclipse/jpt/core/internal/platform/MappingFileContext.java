/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable;
import org.eclipse.jpt.core.internal.content.orm.XmlEntity;
import org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class MappingFileContext extends BaseContext
{
	private XmlRootContentNode ormRoot;
	
	private List<XmlTypeContext> xmlTypeContexts;
	
	
	public MappingFileContext(
			PersistenceUnitContext parentContext, XmlRootContentNode xmlRootContentNode) {
		super(parentContext);
		this.ormRoot = xmlRootContentNode;
		this.xmlTypeContexts = buildXmlTypeContexts();
	}
	
	protected List<XmlTypeContext> buildXmlTypeContexts() {
		List<XmlTypeContext> contexts = new ArrayList<XmlTypeContext>();
		for (XmlTypeMapping typeMapping : this.ormRoot.getEntityMappings().getTypeMappings()) {
			XmlTypeContext xmlTypeContext = buildContext(typeMapping);
			contexts.add(xmlTypeContext);
		}
		return contexts;
	}
	
	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		for (ISequenceGenerator generator : this.ormRoot.getEntityMappings().getSequenceGenerators()) {
			generatorRepository.addGenerator(generator);
		}
		for (ITableGenerator generator : this.ormRoot.getEntityMappings().getTableGenerators()) {
			generatorRepository.addGenerator(generator);
		}
		for (XmlTypeContext context : this.xmlTypeContexts) {
			context.populateGeneratorRepository(generatorRepository);
		}
	}
	
	@Override
	protected void initialize() {
		// nothing yet ...
	}
	
	protected XmlTypeContext buildContext(XmlTypeMapping typeMapping) {
		String key = typeMapping.getKey();
		if (key == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return new XmlEntityContext(this, (XmlEntity) typeMapping);
		}
		else if (key == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
			return new XmlEmbeddableContext(this, (XmlEmbeddable) typeMapping);
		}
		else if (key == IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			return new XmlMappedSuperclassContext(this, (XmlMappedSuperclass) typeMapping);
		}
		else {
			throw new IllegalArgumentException(typeMapping.toString());
		}
	}
		
	protected XmlTypeContext xmlTypeMappingContextFor(IJavaTypeMapping javaTypeMapping) {
		for (XmlTypeContext context : this.xmlTypeContexts) {
			if (javaTypeMapping == context.getJavaTypeMapping()) {
				return context;
			}
		}
		return null;
	}
	
	@Override
	public void refreshDefaults(DefaultsContext parentDefaults, IProgressMonitor monitor) {
		super.refreshDefaults(parentDefaults, monitor);
		ormRoot.getEntityMappings().refreshDefaults(parentDefaults);
		DefaultsContext wrappedDefaultsContext = wrapDefaultsContext(parentDefaults);
		for (XmlTypeContext context : this.xmlTypeContexts) {
			checkCanceled(monitor);
			if (!context.isRefreshed()) {
				context.refreshDefaults(wrappedDefaultsContext, monitor);
			}
		}
	}
	
	private void checkCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}		
	}
	
	private DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY)
					||  key.equals(BaseJpaPlatform.DEFAULT_TABLE_GENERATOR_SCHEMA_KEY)) {
					String schema = ormRoot.getEntityMappings().getSchema();
					if (schema != null) {
						return schema;
					}
				}
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY)) {
					String catalog =  ormRoot.getEntityMappings().getCatalog();
					if (catalog != null) {
						return catalog;
					}
				}
				
				return super.getDefault(key);
			}
		};
	}
	
	public XmlRootContentNode getXmlRootContentNode() {
		return ormRoot;
	}
	
	public PersistenceUnitContext getPersistenceUnitContext() {
		return (PersistenceUnitContext) getParentContext();
	}
	
	public Iterator<XmlTypeContext> typeContexts() {
		return  this.xmlTypeContexts.iterator();
	}
	
	public boolean contains(IPersistentType persistentType) {
		for (XmlTypeContext context : this.xmlTypeContexts) {
			if (persistentType == context.getPersistentType()) {
				return true;
			}
			if (persistentType == context.javaPersistentType()) {
				return true;
			}
		}
		return false;	
	}
	
	Iterator<IPersistentType> persistentTypes() {
		return new TransformationIterator<XmlTypeContext, IPersistentType>(xmlTypeContexts.iterator()) {
			@Override
			protected IPersistentType transform(XmlTypeContext next) {
				return next.getPersistentType();
			}
		};
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		for (XmlTypeContext typeMappingContext : this.xmlTypeContexts) {
			typeMappingContext.addToMessages(messages);
		}
	}
}
