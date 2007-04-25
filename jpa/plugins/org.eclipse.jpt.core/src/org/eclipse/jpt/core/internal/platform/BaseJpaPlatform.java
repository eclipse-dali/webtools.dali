/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaJpaFileContentProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaId;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion;
import org.eclipse.jpt.core.internal.content.orm.OrmXmlJpaFileContentProvider;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlJpaFileContentProvider;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJpaPlatform implements IJpaPlatform
{
	public static final String DEFAULT_TABLE_SCHEMA_KEY = "table.schema";
	public static final String DEFAULT_TABLE_CATALOG_KEY = "table.catalog";
	public static final String DEFAULT_TABLE_NAME_KEY = "table.name";
	public static final String DEFAULT_ACCESS_KEY = "access";
	public static final String DEFAULT_ENTITY_NAME_KEY = "entity.name";
	public static final String DEFAULT_COLUMN_TABLE_KEY = "column.table";
	public static final String DEFAULT_COLUMN_NAME_KEY = "column.name";
	public static final String DEFAULT_JOIN_TABLE_NAME_KEY = "joinTable.name";
	public static final String DEFAULT_TARGET_ENTITY_KEY = "oneToMany.targetEntity";
	public static final String DEFAULT_JOIN_COLUMN_TABLE_KEY = "joinColumn.table";
	public static final String DEFAULT_JOIN_COLUMN_NAME_KEY = "joinColumn.name";
	public static final String DEFAULT_JOIN_COLUMN_REFERENCED_COLUMN_NAME_KEY = "joinColumn.referencedColumnName";
	public static final String DEFAULT_TABLE_GENERATOR_SCHEMA_KEY = "tableGenerator.schema";
	
	private String id;
	
	protected IJpaProject project;
	
	private Collection<IJpaFileContentProvider> contentProviders;
	
	private IContext context;
	
	public String getId() {
		return this.id;
	}
	
	/**
	 * *************
	 * * IMPORTANT *   For INTERNAL use only !!
	 * *************
	 * 
	 * @see IJpaPlatform#setId(String)
	 */
	public void setId(String theId) {
		this.id = theId;
	}
	
	public IJpaProject getProject() {
		return this.project;
	}
	
	public void setProject(IJpaProject jpaProject) {
		this.project = jpaProject;
	}
	
	public Collection<IJpaFileContentProvider> jpaFileContentProviders() {
		if (this.contentProviders == null) {
			this.contentProviders = new ArrayList<IJpaFileContentProvider>();
			this.contentProviders.add(PersistenceXmlJpaFileContentProvider.INSTANCE);
			this.contentProviders.add(JavaJpaFileContentProvider.INSTANCE);
			this.contentProviders.add(OrmXmlJpaFileContentProvider.INSTANCE);
		}
		return this.contentProviders;
	}
	
	public IContext buildProjectContext() {
		this.context = new BaseJpaProjectContext(getProject());
		return this.context;
	}
	
	public IContext buildJavaTypeContext(IContext parentContext, IJavaTypeMapping typeMapping) {
		String key = typeMapping.getKey();
		if (key == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return new JavaEntityContext(parentContext, (JavaEntity) typeMapping);
		}
		else if (key == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
			return new JavaEmbeddableContext(parentContext, (JavaEmbeddable) typeMapping);
		}
		else if (key == IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			return new JavaMappedSuperclassContext(parentContext, (JavaMappedSuperclass) typeMapping);
		}
		else if (key == null) {
			return new JavaNullTypeMappingContext(parentContext, (JavaNullTypeMapping) typeMapping);
		}
		else {
			throw new IllegalArgumentException(typeMapping.toString());
		}
	}
	
	public IContext buildJavaAttributeContext(IContext parentContext, IJavaAttributeMapping attributeMapping) {
		String key = attributeMapping.getKey();
		if (key == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			return new JavaBasicContext(parentContext, (JavaBasic) attributeMapping);
		}
		else if (key == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			return new JavaIdContext(parentContext, (JavaId) attributeMapping);
		}
		else if (key == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			return new JavaVersionContext(parentContext, (JavaVersion) attributeMapping);
		}
		else if (key == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			return new JavaEmbeddedContext(parentContext, (JavaEmbedded) attributeMapping);
		}
		else if (key == IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			return new JavaEmbeddedIdContext(parentContext, (JavaEmbeddedId) attributeMapping);
		}
		else if (key == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return new JavaOneToOneContext(parentContext, (JavaOneToOne) attributeMapping);
		}
		else if (key == IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return new JavaOneToManyContext(parentContext, (JavaOneToMany) attributeMapping);
		}
		else if (key == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return new JavaManyToOneContext(parentContext, (JavaManyToOne) attributeMapping);
		}
		else if (key == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return new JavaManyToManyContext(parentContext, (JavaManyToMany) attributeMapping);
		}
		else if (key == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return new JavaTransientContext(parentContext, (JavaTransient) attributeMapping);
		}
		else if (key == null) {
			return new JavaNullAttributeMappingContext(parentContext, (JavaNullAttributeMapping) attributeMapping);
		}
		else {
			throw new IllegalArgumentException(attributeMapping.toString());
		}
	}
	
	public void resynch(IContext contextHierarchy) {
		((BaseJpaProjectContext) contextHierarchy).refreshDefaults();
	}
	
	public void addToMessages(List<IMessage> messages) {
		BaseJpaProjectContext context = (BaseJpaProjectContext) buildProjectContext();
		context.refreshDefaults();
		context.addToMessages(messages);
	}
	
//	public IGeneratorRepository generatorRepository(IPersistentType persistentType) {
//		return ((BaseJpaProjectContext) context).generatorRepository(persistentType);
//	}
}
