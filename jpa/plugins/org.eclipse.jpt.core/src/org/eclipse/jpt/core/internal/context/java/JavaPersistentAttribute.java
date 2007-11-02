/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaPersistentAttribute extends JavaContextModel
	implements IJavaPersistentAttribute
{
	protected String name;

	
//	protected IJavaAttributeMapping defaultMapping;

//	protected IJavaAttributeMapping specifiedMapping;

	protected JavaPersistentAttributeResource persistentAttributeResource;

	public JavaPersistentAttribute(IJavaPersistentType parent) {
		super(parent);
		//no access to the jpaFactory() in the constructor because the parent is not set yet
//		this.defaultMapping = createJavaAttributeMappingFromMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
//		this.setDefaultMapping(this.nullAttributeMappingProvider().buildMapping(this.attribute, null));
	}
	
	public void initialize(JavaPersistentAttributeResource persistentAttributeResource) {
		this.persistentAttributeResource = persistentAttributeResource;
		this.name = this.name(persistentAttributeResource);
	}

	public IPersistentType getPersistentType() {
		return (IPersistentType) this.parent();
	}

	public ITypeMapping typeMapping() {
		return this.getPersistentType().getMapping();
	}
	
	public String getName() {
		return this.name;
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

//
//	private Iterator<IJavaAttributeMappingProvider> attributeMappingProviders() {
//		return jpaPlatform().javaAttributeMappingProviders();
//	}
//
//	private ListIterator<IDefaultJavaAttributeMappingProvider> defaultAttributeMappingProviders() {
//		return jpaPlatform().defaultJavaAttributeMappingProviders();
//	}
//
//	/**
//	 * the "null" attribute mapping is used when the attribute is neither
//	 * modified with a mapping annotation nor mapped by a "default" mapping
//	 */
//	protected IJavaAttributeMappingProvider nullAttributeMappingProvider() {
//		return JavaNullAttributeMappingProvider.instance();
//	}
//
//	public IJavaAttributeMapping getDefaultMapping() {
//		return defaultMapping;
//	}
//
//	/**
//	 * clients do not set the "default" mapping
//	 */
//	private void setDefaultMapping(IJavaAttributeMapping defaultMapping) {
//		this.setDefaultMappingGen(defaultMapping);
//	}
//
//	public IJavaAttributeMapping getSpecifiedMapping() {
//		return specifiedMapping;
//	}
//
//	/**
//	 * clients do not set the "specified" mapping;
//	 * use #setMappingKey(String)
//	 */
//	private void setSpecifiedMapping(IJavaAttributeMapping specifiedMapping) {
//		this.setSpecifiedMappingGen(specifiedMapping);
//	}
//
//	
//	public IJavaAttributeMapping getMapping() {
//		return (this.specifiedMapping != null) ? this.specifiedMapping : this.defaultMapping;
//	}
//


//
//	public String mappingKey() {
//		return this.getMapping().getKey();
//	}
//
//	/**
//	 * return null if there is no "default" mapping for the attribute
//	 */
//	public String defaultMappingKey() {
//		return this.defaultMapping.getKey();
//	}
//
//	/**
//	 * return null if there is no "specified" mapping for the attribute
//	 */
//	public String specifiedMappingKey() {
//		return (this.specifiedMapping == null) ? null : this.specifiedMapping.getKey();
//	}

//	// TODO support morphing mappings, i.e. copying common settings over
//	// to the new mapping; this can't be done in the same was as XmlAttributeMapping
//	// since we don't know all the possible mapping types
//	public void setSpecifiedMappingKey(String newKey) {
//		String oldKey = this.specifiedMappingKey();
//		if (newKey == oldKey) {
//			return;
//		}
//		IJavaAttributeMapping old = this.getMapping();
//		if (newKey == null) {
//			// remove mapping annotation
//			this.setSpecifiedMapping(null);
//			this.attribute.removeAnnotation(this.declarationAnnotationAdapterForAttributeMappingKey(oldKey));
//		}
//		else {
//			// add or replace mapping annotation
//			this.setSpecifiedMapping(this.attributeMappingProvider(newKey).buildMapping(this.attribute, jpaFactory()));
//			if (oldKey != null) {
//				this.attribute.removeAnnotation(this.declarationAnnotationAdapterForAttributeMappingKey(oldKey));
//			}
//			this.attribute.newMarkerAnnotation(this.declarationAnnotationAdapterForAttributeMappingKey(newKey));
//			this.specifiedMapping.updateFromJava(getAttribute().astRoot());
//		}
//		if (this.eNotificationRequired()) {
//			this.eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING, old, this.getMapping()));
//		}
//	}

//	private DeclarationAnnotationAdapter declarationAnnotationAdapterForAttributeMappingKey(String attributeMappingKey) {
//		return this.attributeMappingProvider(attributeMappingKey).declarationAnnotationAdapter();
//	}
//
//	/**
//	 * throw an exception if the provider is not found
//	 */
//	private IJavaAttributeMappingProvider attributeMappingProvider(String attributeMappingKey) {
//		return jpaPlatform().javaAttributeMappingProvider(attributeMappingKey);
//	}

//	public boolean includes(int offset) {
//		ITextRange fullTextRange = this.fullTextRange();
//		if (fullTextRange == null) {
//			//This happens if the attribute no longer exists in the java.
//			//The text selection event is fired before the update from java so our
//			//model has not yet had a chance to update appropriately. The list of
//			//JavaPersistentAttriubtes is stale at this point.  For now, we are trying
//			//to avoid the NPE, not sure of the ultimate solution to these 2 threads accessing
//			//our model
//			return false;
//		}
//		return fullTextRange.includes(offset);
//	}
//
//	public ITextRange fullTextRange() {
//		return this.attribute.textRange();
//	}
//
//	public ITextRange validationTextRange() {
//		return this.selectionTextRange();
//	}
//
//	public ITextRange selectionTextRange() {
//		return this.attribute.nameTextRange();
//	}

	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		this.persistentAttributeResource = persistentAttributeResource;
		this.setName(this.name(persistentAttributeResource));
	}
	
	protected String name(JavaPersistentAttributeResource persistentAttributeResource) {
		return persistentAttributeResource.getName();	
	}

//	public void updateFromJava(CompilationUnit astRoot) {
//		// synchronize the "specified" mapping with the Java source
//		String jpaKey = this.specifiedMappingKey();
//		IJavaAttributeMappingProvider javaProvider = this.javaAttributeMappingProvider(astRoot);
//		String javaKey = ((javaProvider == null) ? null : javaProvider.key());
//		if (javaKey != jpaKey) {
//			IJavaAttributeMapping old = this.getMapping();
//			if (javaKey == null) {
//				// no mapping annotation found in Java source
//				this.setSpecifiedMapping(null);
//			}
//			else {
//				// the mapping has changed
//				this.setSpecifiedMapping(javaProvider.buildMapping(this.attribute, jpaFactory()));
//			}
//			if (this.eNotificationRequired()) {
//				this.eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING, old, this.getMapping()));
//			}
//		}
//		// once the "specified" mapping is in place, update it from Java;
//		// unless it is null, in which case we update the "default" mapping from Java
//		this.getMapping().updateFromJava(astRoot);
//	}
//
//	/**
//	 * return null if we can't find a mapping annotation on the attribute
//	 */
//	private IJavaAttributeMappingProvider javaAttributeMappingProvider(CompilationUnit astRoot) {
//		for (Iterator<IJavaAttributeMappingProvider> i = this.attributeMappingProviders(); i.hasNext();) {
//			IJavaAttributeMappingProvider provider = i.next();
//			if (this.attribute.containsAnnotation(provider.declarationAnnotationAdapter(), astRoot)) {
//				return provider;
//			}
//		}
//		return null;
//	}
//
//	public String primaryKeyColumnName() {
//		IJavaAttributeMapping mapping = this.getMapping();
//		return (mapping == null) ? null : mapping.primaryKeyColumnName();
//	}
//
//	/**
//	 * the mapping might be "default", but it still might be a "null" mapping...
//	 */
//	public boolean mappingIsDefault() {
//		return this.specifiedMapping == null;
//	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return EmptyIterator.instance();
//		return this.getMapping().candidateValuesFor(pos, filter, astRoot);
	}

//	/**
//	 * check to see whether the "default" mapping has changed
//	 */
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		IJavaAttributeMappingProvider defaultProvider = this.defaultAttributeMappingProvider(defaultsContext);
//		if (defaultProvider.key() == this.defaultMapping.getKey()) {
//			return;
//		}
//		// the "default" mapping has changed
//		IJavaAttributeMapping old = this.getMapping();
//		this.setDefaultMapping(defaultProvider.buildMapping(this.attribute, jpaFactory()));
//		this.defaultMapping.updateFromJava(defaultsContext.astRoot());
//		if (this.eNotificationRequired()) {
//			this.eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING, old, this.getMapping()));
//		}
//	}

//	/**
//	 * return the first(?) provider that can supply a "default" mapping for the attribute;
//	 * return the null provider if we can't find a provider
//	 */
//	private IJavaAttributeMappingProvider defaultAttributeMappingProvider(DefaultsContext defaultsContext) {
//		for (Iterator<IDefaultJavaAttributeMappingProvider> i = this.defaultAttributeMappingProviders(); i.hasNext();) {
//			IDefaultJavaAttributeMappingProvider provider = i.next();
//			if (provider.defaultApplies(this.attribute, defaultsContext)) {
//				return provider;
//			}
//		}
//		return this.nullAttributeMappingProvider();
//	}
//
//	public boolean isOverridableAttribute() {
//		return this.getMapping().isOverridableAttributeMapping();
//	}
//
//	public boolean isOverridableAssociation() {
//		return this.getMapping().isOverridableAssociationMapping();
//	}
//
//	public boolean isIdAttribute() {
//		return this.getMapping().isIdMapping();
//	}
}
