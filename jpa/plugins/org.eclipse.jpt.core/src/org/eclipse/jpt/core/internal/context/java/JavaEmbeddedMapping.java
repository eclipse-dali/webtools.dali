/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Embedded;


public class JavaEmbeddedMapping extends JavaAttributeMapping implements IJavaEmbeddedMapping
{
//	protected EList<IAttributeOverride> specifiedAttributeOverrides;
//
//	protected EList<IAttributeOverride> defaultAttributeOverrides;
//
//	private IEmbeddable embeddable;

	public JavaEmbeddedMapping(IJavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Embedded.ANNOTATION_NAME;
	}
	
//	public EList<IAttributeOverride> getAttributeOverrides() {
//		EList<IAttributeOverride> list = new EObjectEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_EMBEDDED__ATTRIBUTE_OVERRIDES);
//		list.addAll(getSpecifiedAttributeOverrides());
//		list.addAll(getDefaultAttributeOverrides());
//		return list;
//	}
//
//	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
//		if (specifiedAttributeOverrides == null) {
//			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES);
//		}
//		return specifiedAttributeOverrides;
//	}
//
//	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
//		if (defaultAttributeOverrides == null) {
//			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES);
//		}
//		return defaultAttributeOverrides;
//	}
//
//	public IEmbeddable embeddable() {
//		return this.embeddable;
//	}
//
//	@Override
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		super.refreshDefaults(defaultsContext);
//		refreshEmbeddable(defaultsContext);
//	}
//
//	private void refreshEmbeddable(DefaultsContext defaultsContext) {
//		this.embeddable = embeddableFor(getAttribute(), defaultsContext);
//	}
//
//	@Override
//	public void updateFromJava(CompilationUnit astRoot) {
//		super.updateFromJava(astRoot);
//		updateAttributeOverridesFromJava(astRoot);
//	}
//
//	/**
//	 * here we just worry about getting the attribute override lists the same size;
//	 * then we delegate to the attribute overrides to synch themselves up
//	 */
//	private void updateAttributeOverridesFromJava(CompilationUnit astRoot) {
//		// synchronize the model attribute overrides with the Java source
//		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
//		int persSize = attributeOverrides.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaAttributeOverride attributeOverride = (JavaAttributeOverride) attributeOverrides.get(i);
//			if (attributeOverride.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			attributeOverride.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model attribute overrides beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				attributeOverrides.remove(persSize);
//			}
//		}
//		else {
//			// add new model attribute overrides until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaAttributeOverride attributeOverride = this.createJavaAttributeOverride(javaSize);
//				if (attributeOverride.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getSpecifiedAttributeOverrides().add(attributeOverride);
//					attributeOverride.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	public IAttributeOverride attributeOverrideNamed(String name) {
//		return (IAttributeOverride) overrideNamed(name, getAttributeOverrides());
//	}
//
//	public boolean containsAttributeOverride(String name) {
//		return containsOverride(name, getAttributeOverrides());
//	}
//
//	public boolean containsSpecifiedAttributeOverride(String name) {
//		return containsOverride(name, getSpecifiedAttributeOverrides());
//	}
//
//	private IOverride overrideNamed(String name, List<? extends IOverride> overrides) {
//		for (IOverride override : overrides) {
//			String overrideName = override.getName();
//			if (overrideName == null && name == null) {
//				return override;
//			}
//			if (overrideName != null && overrideName.equals(name)) {
//				return override;
//			}
//		}
//		return null;
//	}
//
//	private boolean containsOverride(String name, List<? extends IOverride> overrides) {
//		return overrideNamed(name, overrides) != null;
//	}
//
//	public Iterator<String> allOverridableAttributeNames() {
//		return new TransformationIterator<IPersistentAttribute, String>(this.allOverridableAttributes()) {
//			@Override
//			protected String transform(IPersistentAttribute attribute) {
//				return attribute.getName();
//			}
//		};
//	}
//
//	public Iterator<IPersistentAttribute> allOverridableAttributes() {
//		if (this.embeddable() == null) {
//			return EmptyIterator.instance();
//		}
//		return new FilteringIterator<IPersistentAttribute>(this.embeddable().getPersistentType().attributes()) {
//			@Override
//			protected boolean accept(Object o) {
//				return ((IPersistentAttribute) o).isOverridableAttribute();
//			}
//		};
//	}
//
//	public IAttributeOverride createAttributeOverride(int index) {
//		return createJavaAttributeOverride(index);
//	}
//
//	private JavaAttributeOverride createJavaAttributeOverride(int index) {
//		return JavaAttributeOverride.createAttributeOverride(new AttributeOverrideOwner(this), this.getAttribute(), index);
//	}
//
//	@Override
//	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
//		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
//		for (IAttributeOverride override : this.getAttributeOverrides()) {
//			result = ((JavaAttributeOverride) override).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
//		return null;
//	}
//
	//******* static methods *********
	
	public static IEmbeddable embeddableFor(IJavaPersistentAttribute persistentAttribute) {
		String qualifiedTypeName = persistentAttribute.getPersistentAttributeResource().getQualifiedTypeName();
		if (qualifiedTypeName == null) {
			return null;
		}
		IPersistentType persistentType = persistentAttribute.persistenceUnit().persistentType(qualifiedTypeName);
		if (persistentType != null) {
			if (persistentType.mappingKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
				return (IEmbeddable) persistentType.getMapping();
			}
		}
		return null;
	}

}
