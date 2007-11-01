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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Annotation;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class JavaPersistentType extends JavaContextModel implements IJavaPersistentType
{
	protected String name;
	
	protected IJavaTypeMapping mapping;

	protected final List<IJavaPersistentAttribute> attributes;

	protected AccessType access;

	/**
	 * Store the parentPersistentType during default calculation.  This will
	 * be the first persisentType found in the hierarchy, the JPA spec allows
	 * for non-persistent types to be part of the hierarchy.
	 * Example:
	 * 
	 * @Entity public abstract class Model {}
	 * 
	 * public abstract class Animal extends Model {}
	 * 
	 * @Entity public class Cat extends Animal {}
	 * 
	 * If this is the Cat JavaPersistentType then parentPersistentType is the Model JavaPersistentType
	 * The parentPersistentType could be found in java or xml.
	 */
	protected IPersistentType parentPersistentType;

	protected JavaPersistentTypeResource persistentTypeResource;

	public JavaPersistentType(IClassRef parent) {
		super(parent);
		this.attributes = new ArrayList<IJavaPersistentAttribute>();
		this.mapping = createJavaTypeMappingFromMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
	}
	
	public String getName() {
		return this.name;
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public IJavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String mappingKey() {
		return getMapping().getKey();
	}
	
	public void setMappingKey(String key) {
		if (key == getMapping().getKey()) {
			return;
		}
		IJavaTypeMapping newMapping = createJavaTypeMappingFromMappingKey(key);
		this.persistentTypeResource.setMappingAnnotation(newMapping.annotationName());
		setMapping(newMapping);		
	}
	
	protected void setMapping(IJavaTypeMapping newMapping) {
		IJavaTypeMapping oldMapping = this.mapping;
		this.mapping = newMapping;	
		firePropertyChanged(IPersistentType.MAPPING_PROPERTY, oldMapping, newMapping);
	}
	
	public boolean isMapped() {
		return getMapping().isMapped();
	}

	public AccessType access() {
		return this.access;
	}
	
	protected void setAccess(AccessType newAccess) {
		AccessType oldAccess = this.access;
		this.access = newAccess;
		firePropertyChanged(ACCESS_PROPERTY, oldAccess, newAccess);
	}

	protected Iterator<JavaPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<JavaPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(Object o) {
				return attributeName.equals(((JavaPersistentAttribute) o).getName());
			}
		};
	}

	public JavaPersistentAttribute attributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

//	public IPersistentAttribute resolveAttribute(String attributeName) {
//		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
//		if (stream.hasNext()) {
//			JavaPersistentAttribute attribute = stream.next();
//			return (stream.hasNext()) ? null /*more than one*/: attribute;
//		}
//		return (parentPersistentType() == null) ? null : parentPersistentType().resolveAttribute(attributeName);
//	}
	
	public ListIterator<IJavaPersistentAttribute> attributes() {
		return new CloneListIterator<IJavaPersistentAttribute>(this.attributes);
	}
	
	public int attributesSize() {
		return this.attributes.size();
	}
	
	private void addAttribute(IJavaPersistentAttribute attribute) {
		addItemToList(attribute, this.attributes, IPersistentType.ATTRIBUTES_LIST);
	}

	private void removeAttribute(IJavaPersistentAttribute attribute) {
		removeItemFromList(attribute, this.attributes, IPersistentType.ATTRIBUTES_LIST);
	}
	
	public Iterator<String> attributeNames() {
		return this.attributeNames(this.attributes());
	}
	
	private Iterator<String> attributeNames(Iterator<? extends IPersistentAttribute> attrs) {
		return new TransformationIterator<IPersistentAttribute, String>(attrs) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}
	
	public Iterator<IPersistentAttribute> allAttributes() {
		return new CompositeIterator<IPersistentAttribute>(new TransformationIterator<IPersistentType, Iterator<IPersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<IPersistentAttribute> transform(IPersistentType pt) {
				//TODO how to remove this warning?
				return (Iterator<IPersistentAttribute>) pt.attributes();
			}
		});
	}
	
	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		Iterator<String> values = this.mapping.candidateValuesFor(pos, filter, astRoot);
		if (values != null) {
			return values;
		}
//		for (Iterator<JavaPersistentAttribute> stream = attributes(); stream.hasNext();) {
//			values = stream.next().candidateValuesFor(pos, filter, astRoot);
//			if (values != null) {
//				return values;
//			}
//		}
		return null;
	}
//
//	public IJpaContentNode contentNodeAt(int offset) {
//		for (Iterator<JavaPersistentAttribute> i = attributes(); i.hasNext();) {
//			JavaPersistentAttribute persistentAttribute = i.next();
//			if (persistentAttribute.includes(offset)) {
//				return persistentAttribute;
//			}
//		}
//		return null;
//	}

//	public boolean includes(int offset) {
//		ITextRange fullTextRange = this.fullTextRange();
//		if (fullTextRange == null) {
//			//This happens if the type no longer exists in the java (rename in editor).
//			//The text selection event is fired before the update from java so our
//			//model has not yet had a chance to update appropriately.  For now, avoid the NPE, 
//			//not sure of the ultimate solution to these 2 threads accessing our model
//			return false;
//		}
//		return fullTextRange.includes(offset);
//	}
//
//	public ITextRange fullTextRange() {
//		return this.persistentTypeResource.fullTextRange();
//	}
//
//	public ITextRange validationTextRange(CompilationUnit astRoot) {
//		return this.selectionTextRange();
//	}
//
//	public ITextRange selectionTextRange() {
//		return this.persistentTypeResource.textRange();
//	}


	public Iterator<IPersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<IPersistentType>(this) {
			@Override
			protected IPersistentType nextLink(IPersistentType pt) {
				return pt.parentPersistentType();
			}
		};
	}

	public IPersistentType parentPersistentType() {
		return this.parentPersistentType;
	}

	// ******************** Uupdating **********************
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
		updateParentPersistentType(persistentTypeResource);
		updateAccess(persistentTypeResource);
		updateName(persistentTypeResource);
		updateMapping(persistentTypeResource);
		updatePersistentAttributes(persistentTypeResource);
	}
	
	/**
	 * Check the access "specified" by the java resource model.
	 * 		If this is not null then use that as the access. (validation will handle where this doesn't match inheritance)
	 * 		If null then set to parentPersistentType access.
	 * 		Default to FIELD if no parentPersistentType.
	 */
	protected void updateAccess(JavaPersistentTypeResource persistentTypeResource) {
		AccessType javaAccess = AccessType.fromJavaResourceModel(persistentTypeResource.getAccess());
		if (javaAccess == null) {
			if (parentPersistentType() != null) {
				javaAccess = parentPersistentType().access();
			}
			if (javaAccess == null) {
				javaAccess = AccessType.FIELD;
			}
		}
		setAccess(javaAccess);
	}

	protected void updateName(JavaPersistentTypeResource persistentTypeResource) {
		setName(persistentTypeResource.getQualifiedName());	
	}
	
	protected void updateMapping(JavaPersistentTypeResource persistentTypeResource) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(persistentTypeResource);
		if (getMapping().annotationName() != javaMappingAnnotationName) {
			setMapping(createJavaTypeMappingFromAnnotation(javaMappingAnnotationName));
		}
		getMapping().update(persistentTypeResource);
	}
	
	protected IJavaTypeMapping createJavaTypeMappingFromMappingKey(String key) {
		return jpaPlatform().createJavaTypeMappingFromMappingKey(key, this);
	}
	
	protected IJavaTypeMapping createJavaTypeMappingFromAnnotation(String annotationName) {
		return jpaPlatform().createJavaTypeMappingFromAnnotation(annotationName, this);
	}

	protected String javaMappingAnnotationName(JavaPersistentTypeResource typeResource) {
		Annotation mappingAnnotation = (Annotation) typeResource.mappingAnnotation();
		if (mappingAnnotation != null) {
			return mappingAnnotation.getAnnotationName();
		}
		return null;
	}

	protected void updatePersistentAttributes(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaPersistentAttribute> contextAttributes = attributes();
		Iterator<JavaPersistentAttributeResource> resourceAttributes = persistentTypeResource.fields();
		if (access() == AccessType.PROPERTY) {
			resourceAttributes = persistentTypeResource.properties();
		}		
		
		while (contextAttributes.hasNext()) {
			IJavaPersistentAttribute persistentAttribute = contextAttributes.next();
			if (resourceAttributes.hasNext()) {
				persistentAttribute.update(resourceAttributes.next());
			}
			else {
				removeAttribute(persistentAttribute);
			}
		}
		
		while (resourceAttributes.hasNext()) {
			IJavaPersistentAttribute persistentAttribute = createAttribute();
			addAttribute(persistentAttribute);
			persistentAttribute.update(resourceAttributes.next());
		}
	}
	
	protected IJavaPersistentAttribute createAttribute() {
		return jpaFactory().createJavaPersistentAttribute(this);
	}
	
	public void updateParentPersistentType(JavaPersistentTypeResource persistentTypeResource) {
		//TODO do we need any change notification for this?
		this.parentPersistentType = parentPersistentType(persistentTypeResource.getSuperClassQualifiedName());
	}
	
	protected IPersistentType parentPersistentType(String fullyQualifiedTypeName) {
		IPersistentType possibleParent = possibleParent(fullyQualifiedTypeName);
		if (possibleParent == null) {
			return null;
		}
		if (possibleParent.isMapped()) {
			return possibleParent;
		}
		return possibleParent.parentPersistentType();
	}

	/**
	 * JPA spec supports the case where there are non-persistent types in the hierarchy
	 * This will check for a PersistentType with the given name in this PersistenceUnit.
	 * If it is not found then find the JavaPersistentTypeResource and look for its parent type
	 */
	protected IPersistentType possibleParent(String fullyQualifiedTypeName) {
		IPersistentType possibleParent = persistentType(fullyQualifiedTypeName);
		if (possibleParent != null) {
			return possibleParent;
		}
		JavaPersistentTypeResource javaPersistentTypeResource = jpaProject().javaPersistentTypeResource(fullyQualifiedTypeName);
		if (javaPersistentTypeResource != null) {
			return possibleParent(javaPersistentTypeResource.getSuperClassQualifiedName());
		}
		return null;		
	}
	
	protected IPersistentType persistentType(String fullyQualifiedTypeName) {
		return persistenceUnit().persistentType(fullyQualifiedTypeName);
	}

}