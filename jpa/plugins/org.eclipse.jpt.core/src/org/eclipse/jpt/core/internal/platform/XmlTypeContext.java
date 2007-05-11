/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlBasic;
import org.eclipse.jpt.core.internal.content.orm.XmlEmbedded;
import org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.internal.content.orm.XmlId;
import org.eclipse.jpt.core.internal.content.orm.XmlManyToMany;
import org.eclipse.jpt.core.internal.content.orm.XmlManyToOne;
import org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToMany;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToOne;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.content.orm.XmlTransient;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlVersion;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class XmlTypeContext extends BaseContext
	implements TypeContext
{
	private XmlTypeMapping xmlTypeMapping;
	
	private JavaTypeContext javaTypeContext;
	
	private Collection<XmlAttributeContext> attributeMappingContexts;
	
	private Collection<XmlAttributeContext> virtualAttributeMappingContexts;
	
	private boolean refreshed;
	
	public XmlTypeContext(MappingFileContext parentContext, XmlTypeMapping xmlTypeMapping) {
		super(parentContext);
		this.xmlTypeMapping = xmlTypeMapping;
		this.attributeMappingContexts =  new ArrayList<XmlAttributeContext>();
		this.virtualAttributeMappingContexts =  new ArrayList<XmlAttributeContext>();
	}
	
	public void initialize() {
		JavaPersistentType javaPersistentType = javaPersistentType();
		PersistenceUnitContext puContext = getMappingFileContext().getPersistenceUnitContext();
		if (javaPersistentType != null) {
			if (puContext.containsDuplicateJavaPersistentType(javaPersistentType)) {
				return;
			}
			JavaTypeContext javaContext = 
				(JavaTypeContext) getPlatform().buildJavaTypeContext(this, javaPersistentType.getMapping());
			XmlTypeContext duplicate = puContext.xmlTypeMappingContextFor(javaPersistentType.getMapping());
			if (duplicate != null) {
				duplicate.setDuplicateJavaTypeMapping();
				puContext.addDuplicateJpaFile(javaContext);
				return;
			}
			this.javaTypeContext = javaContext;
		}
		
		initializeAttributeMappingContexts();
	}
	
	protected void initializeAttributeMappingContexts() {
		for (XmlAttributeMapping xmlAttributeMapping: this.xmlTypeMapping.getPersistentType().getSpecifiedAttributeMappings()) {
			this.attributeMappingContexts.add(buildContext(xmlAttributeMapping));
		}
	}
	
	protected XmlAttributeContext buildContext(XmlAttributeMapping attributeMapping) {
		String key = attributeMapping.getKey();
		if (key == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			return new XmlIdContext(this, (XmlId) attributeMapping);
		}
		else if (key == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			return new XmlBasicContext(this, (XmlBasic) attributeMapping);
		}
		else if (key == IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return new XmlOneToManyContext(this, (XmlOneToMany) attributeMapping);
		}
		else if (key == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return new XmlManyToOneContext(this, (XmlManyToOne) attributeMapping);
		}
		else if (key == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return new XmlManyToManyContext(this, (XmlManyToMany) attributeMapping);
		}
		else if (key == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return new XmlTransientContext(this, (XmlTransient) attributeMapping);
		}
		else if (key == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			return new XmlEmbeddedContext(this, (XmlEmbedded) attributeMapping);
		}
		else if (key == IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			return new XmlEmbeddedIdContext(this, (XmlEmbeddedId) attributeMapping);
		}
		else if (key == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			return new XmlVersionContext(this, (XmlVersion) attributeMapping);
		}
		else if (key == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return new XmlOneToOneContext(this, (XmlOneToOne) attributeMapping);
		}
		else if (key == null) {
			return new XmlNullAttributeMappingContext(this, (XmlNullAttributeMapping) attributeMapping);
		}
		else {
			throw new IllegalArgumentException(attributeMapping.toString());
		}
	}
	
	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		//override as necessary
	}

	protected Collection<XmlAttributeContext> getXmlAttributeContexts() {
		return this.attributeMappingContexts;
	}
	
	protected MappingFileContext getMappingFileContext() {
		return (MappingFileContext) getParentContext();
	}
	
	protected XmlTypeMapping getXmlTypeMapping() {
		return this.xmlTypeMapping;
	}
	
	protected JavaTypeContext getJavaPersistentTypeContext() {
		return this.javaTypeContext;
	}
	
	protected IJavaTypeMapping getJavaTypeMapping() {
		if (this.javaTypeContext != null) {
			return this.javaTypeContext.getPersistentType().getMapping();
		}
		return null;
	}
	
	protected JavaPersistentType javaPersistentType() {
		return this.xmlTypeMapping.getPersistentType().findJavaPersistentType();
	}
	
	protected IJavaTypeMapping javaTypeMapping() {
		JavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType != null) {
			return javaPersistentType.getMapping();
		}
		return null;
	}
	/**
	 * A java class is specified to be mapped in to orm.xml files in the same persistence unit.
	 * Null out the javaTypeMapping for this case, so that the context is no longer a particular
	 * orm.xml file.
	 */
	public void setDuplicateJavaTypeMapping() {
		this.javaTypeContext = null;
	}
	
	public DefaultsContext wrapDefaultsContext(final DefaultsContext defaultsContext) {
		return new DefaultsContext() {
			public Object getDefault(String key) {
				return XmlTypeContext.this.getDefault(key, defaultsContext);
			}
			
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return defaultsContext.persistentType(fullyQualifiedTypeName);
			}
		};
	}
	
	
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		return defaultsContext.getDefault(key);
	}	
	
	public XmlPersistentType getPersistentType() {
		return getXmlTypeMapping().getPersistentType();
	}
	
	public boolean isRefreshed() {
		return this.refreshed;
	}

	public void refreshDefaults(DefaultsContext parentDefaults) {
		super.refreshDefaults(parentDefaults);
		this.refreshed = true;
		if (this.javaTypeContext != null) {
			this.javaTypeContext.refreshDefaults(parentDefaults);
		}
		refreshPersistentType(parentDefaults);
		DefaultsContext wrappedDefaultsContext = wrapDefaultsContext(parentDefaults);
		refreshTableContext(wrappedDefaultsContext);
		this.xmlTypeMapping.refreshDefaults(wrappedDefaultsContext);
		
		refreshAttributeMappingContextDefaults(wrappedDefaultsContext);
	}
	
	protected void refreshTableContext(DefaultsContext defaultsContext) {
	}
	
	public void refreshAttributeMappingContextDefaults(DefaultsContext defaultsContext) {
		for (XmlAttributeContext context : this.attributeMappingContexts) {
			context.refreshDefaults(context.wrapDefaultsContext(defaultsContext));
		}
		for (XmlAttributeContext context : this.virtualAttributeMappingContexts) {
			context.refreshDefaults(context.wrapDefaultsContext(defaultsContext));
		}
	}
	
	
	protected void refreshPersistentType(DefaultsContext defaultsContext) {
		XmlPersistentType xmlPersistentType = this.getXmlTypeMapping().getPersistentType();
		xmlPersistentType.refreshDefaults(defaultsContext);
		//get the java attribute names
		//check the specified xml attributes (those with an element in the xml), add
		//a xmlAttribute mapping for any java attribute name with default = true
		//if we already have an xml attribute mapping, then do nothing, 
		//the defaults refresh will get forwarded on the attribute mappings later.
		Collection<IPersistentAttribute> javaAttributes = javaAttributes();
		Collection<String> javaAttributeNames = new ArrayList<String>();
		for (IPersistentAttribute javaAttribute : javaAttributes) {
			String javaAttributeName = javaAttribute.getName();
			javaAttributeNames.add(javaAttributeName);
			XmlPersistentAttribute xmlAttribute = xmlPersistentType.attributeNamed(javaAttributeName);
			if (xmlAttribute == null) {
				createAndAddXmlAttributeFrom(javaAttribute, xmlPersistentType);
			}
		}
		
		//remove any default mappings that are not included in the javaAttributeNames collection
		Collection<XmlAttributeMapping> mappingsToRemove = new ArrayList<XmlAttributeMapping>();
		for (XmlAttributeMapping mapping : xmlPersistentType.getVirtualAttributeMappings()) {
			if (!javaAttributeNames.contains(mapping.getPersistentAttribute().getName())) {
				mappingsToRemove.add(mapping);
			}
		}
		
		xmlPersistentType.getVirtualAttributeMappings().removeAll(mappingsToRemove);

		for (XmlAttributeMapping xmlAttributeMapping : this.xmlTypeMapping.getPersistentType().getVirtualAttributeMappings()) {
			this.virtualAttributeMappingContexts.add(buildContext(xmlAttributeMapping));
		}
}
	
	private void createAndAddXmlAttributeFrom(IPersistentAttribute javaAttribute, XmlPersistentType persistentType) {
		//TODO also need to check xml mapping meta data complete flags and 
		//probably move all of this code to the context classes
		XmlAttributeMapping xmlAttributeMapping = null;
		String mappingKey;
		if (persistentType.getMapping().isXmlMetadataComplete()) {
			mappingKey = javaAttribute.defaultMappingKey();
		}
		else {
			mappingKey = javaAttribute.getMapping().getKey();
		}
		if (mappingKey == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlBasic();
		}
		else if (mappingKey == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlId();
		}
		else if (mappingKey == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlManyToMany();
		}
		else if (mappingKey == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlManyToOne();
		}
		else if (mappingKey == IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlOneToMany();
		}
		else if (mappingKey == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlTransient();
		}
		else if (mappingKey == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlEmbedded();
		}
		else if (mappingKey == IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlEmbeddedId();
		}
		else if (mappingKey == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlVersion();
		}
		else if (mappingKey == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlOneToOne();
		}
		else if (mappingKey == null) {
			xmlAttributeMapping = OrmFactory.eINSTANCE.createXmlNullAttributeMapping();
		}
		//TODO this isn't the way to do it, should update all the settings on the default mapping
		// during the refresh defaults of attribute mappings
		//xmlAttributeMapping.initializeFromAttributeMapping(javaAttribute.getMapping());
		persistentType.getVirtualAttributeMappings().add(xmlAttributeMapping);
		xmlAttributeMapping.getPersistentAttribute().setName(javaAttribute.getName());
	}
	
	protected Collection<IPersistentAttribute> javaAttributes() {
		Collection<IPersistentAttribute> javaAttributes = new ArrayList<IPersistentAttribute>();
		JavaPersistentType javaPersistentType = javaPersistentType();
		if (javaPersistentType != null) {
			for (Iterator<IPersistentAttribute> i = javaPersistentType.attributes(); i.hasNext(); ) {
				IPersistentAttribute persistentAttribute = i.next();
				javaAttributes.add(persistentAttribute);
			}
		}
		return javaAttributes;
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addClassMessages(messages);
		
		for (XmlAttributeContext context : this.attributeMappingContexts) {
			context.addToMessages(messages);
		}
		for (XmlAttributeContext context : this.virtualAttributeMappingContexts) {
			context.addToMessages(messages);
		}
	}
	
	protected void addClassMessages(List<IMessage> messages) {
		addUnspecifiedClassMessage(messages);
		addUnresolvedClassMessage(messages);
	}
	
	protected void addUnspecifiedClassMessage(List<IMessage> messages) {
		XmlPersistentType persistentType = xmlTypeMapping.getPersistentType();
		if (StringTools.stringIsEmpty(persistentType.getClass_())) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CLASS,
					persistentType, persistentType.classTextRange())
			);
		}
	}
	
	protected void addUnresolvedClassMessage(List<IMessage> messages) {
		XmlPersistentType persistentType = xmlTypeMapping.getPersistentType();
		if (! StringTools.stringIsEmpty(persistentType.getClass_())
				&& persistentType.findJdtType() == null) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.PERSISTENT_TYPE_UNRESOLVED_CLASS,
					new String[] {persistentType.getClass_()},
					persistentType, persistentType.classTextRange())
			);
		}
	}
}