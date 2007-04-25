/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Embedded</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbedded()
 * @model kind="class"
 * @generated
 */
public class JavaEmbedded extends JavaAttributeMapping implements IEmbedded
{
	/**
	 * The cached value of the '{@link #getSpecifiedAttributeOverrides() <em>Specified Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> specifiedAttributeOverrides;

	/**
	 * The cached value of the '{@link #getDefaultAttributeOverrides() <em>Default Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> defaultAttributeOverrides;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.EMBEDDED);

	private IEmbeddable embeddable;

	protected JavaEmbedded() {
		throw new UnsupportedOperationException("Use JavaEmbedded(Attribute) instead");
	}

	protected JavaEmbedded(Attribute attribute) {
		super(attribute);
	}

	/**
	 * check for changes to the 'specifiedJoinColumns' and
	 * 'specifiedInverseJoinColumns' lists so we can notify the
	 * model adapter of any changes;
	 * also listen for changes to the 'defaultJoinColumns' and
	 * 'defaultInverseJoinColumns' lists so we can spank the developer
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IEntity.class)) {
			case JpaCoreMappingsPackage.IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				attributeOverridesChanged(notification);
				break;
			default :
				break;
		}
	}

	void attributeOverridesChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				attributeOverrideAdded(notification.getPosition(), (JavaAttributeOverride) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				attributeOverridesAdded(notification.getPosition(), (List<JavaAttributeOverride>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				attributeOverrideRemoved(notification.getPosition(), (JavaAttributeOverride) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					attributeOverridesCleared((List<JavaAttributeOverride>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					attributeOverridesRemoved((int[]) notification.getNewValue(), (List<JavaAttributeOverride>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					attributeOverrideSet(notification.getPosition(), (JavaAttributeOverride) notification.getOldValue(), (JavaAttributeOverride) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				attributeOverrideMoved(notification.getOldIntValue(), notification.getPosition(), (JavaAttributeOverride) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	// ********** jpa model -> java annotations **********
	/**
	 * slide over all the annotations that follow the new join column
	 */
	public void attributeOverrideAdded(int index, JavaAttributeOverride attributeOverride) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (attributeOverride.annotation(getAttribute().astRoot()) == null) {
			this.synchAttributeOverrideAnnotationsAfterAdd(index + 1);
			attributeOverride.newAnnotation();
		}
	}

	public void attributeOverridesAdded(int index, List<JavaAttributeOverride> attributeOverrides) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!attributeOverrides.isEmpty() && (attributeOverrides.get(0).annotation(getAttribute().astRoot()) == null)) {
			this.synchAttributeOverrideAnnotationsAfterAdd(index + attributeOverrides.size());
			for (JavaAttributeOverride attributeOverride : attributeOverrides) {
				attributeOverride.newAnnotation();
			}
		}
	}

	public void attributeOverrideRemoved(int index, JavaAttributeOverride attributeOverride) {
		attributeOverride.removeAnnotation();
		this.synchAttributeOverrideAnnotationsAfterRemove(index);
	}

	public void attributeOverridesRemoved(int[] indexes, List<JavaAttributeOverride> attributeOverrides) {
		for (JavaAttributeOverride attributeOverride : attributeOverrides) {
			attributeOverride.removeAnnotation();
		}
		this.synchAttributeOverrideAnnotationsAfterRemove(indexes[0]);
	}

	public void attributeOverridesCleared(List<JavaAttributeOverride> attributeOverrides) {
		for (JavaAttributeOverride attributeOverride : attributeOverrides) {
			attributeOverride.removeAnnotation();
		}
	}

	public void attributeOverrideSet(int index, JavaAttributeOverride oldAttributeOverride, JavaAttributeOverride newAttributeOverride) {
		newAttributeOverride.newAnnotation();
	}

	public void attributeOverrideMoved(int sourceIndex, int targetIndex, JavaAttributeOverride attributeOverride) {
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch((JavaAttributeOverride) attributeOverrides.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchAttributeOverrideAnnotationsAfterAdd(int index) {
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		for (int i = attributeOverrides.size(); i-- > index;) {
			this.synch((JavaAttributeOverride) attributeOverrides.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchAttributeOverrideAnnotationsAfterRemove(int index) {
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		for (int i = index; i < attributeOverrides.size(); i++) {
			this.synch((JavaAttributeOverride) attributeOverrides.get(i), i);
		}
	}

	private void synch(JavaAttributeOverride attributeOverride, int index) {
		attributeOverride.moveAnnotation(index);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_EMBEDDED;
	}

	public EList<IAttributeOverride> getAttributeOverrides() {
		EList<IAttributeOverride> list = new BasicEList<IAttributeOverride>();
		list.addAll(getSpecifiedAttributeOverrides());
		list.addAll(getDefaultAttributeOverrides());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEmbedded_SpecifiedAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
		if (specifiedAttributeOverrides == null) {
			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES);
		}
		return specifiedAttributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEmbedded_DefaultAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
		if (defaultAttributeOverrides == null) {
			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES);
		}
		return defaultAttributeOverrides;
	}

	public IEmbeddable embeddable() {
		return this.embeddable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getSpecifiedAttributeOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getDefaultAttributeOverrides()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__ATTRIBUTE_OVERRIDES :
				return getAttributeOverrides();
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return getSpecifiedAttributeOverrides();
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				return getDefaultAttributeOverrides();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				getSpecifiedAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
				getDefaultAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__ATTRIBUTE_OVERRIDES :
				return !getAttributeOverrides().isEmpty();
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return specifiedAttributeOverrides != null && !specifiedAttributeOverrides.isEmpty();
			case JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				return defaultAttributeOverrides != null && !defaultAttributeOverrides.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IEmbedded.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_EMBEDDED__ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IEMBEDDED__ATTRIBUTE_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES;
				default :
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IEmbedded.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IEMBEDDED__ATTRIBUTE_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_EMBEDDED__ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getKey() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		refreshEmbeddable(defaultsContext);
	}

	private void refreshEmbeddable(DefaultsContext defaultsContext) {
		this.embeddable = embeddableFor(getAttribute(), defaultsContext);
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		updateAttributeOverridesFromJava(astRoot);
	}

	/**
	 * here we just worry about getting the attribute override lists the same size;
	 * then we delegate to the attribute overrides to synch themselves up
	 */
	private void updateAttributeOverridesFromJava(CompilationUnit astRoot) {
		// synchronize the model attribute overrides with the Java source
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		int persSize = attributeOverrides.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaAttributeOverride attributeOverride = (JavaAttributeOverride) attributeOverrides.get(i);
			if (attributeOverride.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			attributeOverride.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model attribute overrides beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				attributeOverrides.remove(persSize);
			}
		}
		else {
			// add new model attribute overrides until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaAttributeOverride attributeOverride = this.createJavaAttributeOverride(javaSize);
				if (attributeOverride.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getSpecifiedAttributeOverrides().add(attributeOverride);
					attributeOverride.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	public boolean containsAttributeOverride(String name) {
		return containsAttributeOverride(name, getAttributeOverrides());
	}

	public boolean containsSpecifiedAttributeOverride(String name) {
		return containsAttributeOverride(name, getSpecifiedAttributeOverrides());
	}

	private boolean containsAttributeOverride(String name, List<IAttributeOverride> attributeOverrides) {
		for (IAttributeOverride attributeOverride : attributeOverrides) {
			String attributeOverrideName = attributeOverride.getName();
			if (attributeOverrideName != null && attributeOverrideName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Iterator<String> allOverridableAttributeNames() {
		if (embeddable() != null) {
			return new TransformationIterator(new FilteringIterator(embeddable().getPersistentType().attributes()) {
				protected boolean accept(Object o) {
					String key = ((IPersistentAttribute) o).getMappingKey();
					return key == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || key == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
				}
			}) {
				protected Object transform(Object next) {
					return ((IPersistentAttribute) next).getName();
				}
			};
		}
		return EmptyIterator.instance();
	}

	public IAttributeOverride createAttributeOverride(int index) {
		return createJavaAttributeOverride(index);
	}

	private JavaAttributeOverride createJavaAttributeOverride(int index) {
		return JavaAttributeOverride.createAttributeOverride(new AttributeOverrideOwner(this), this.getAttribute(), index);
	}

	//******* static methods *********
	public static IEmbeddable embeddableFor(Attribute attribute, DefaultsContext defaultsContext) {
		String resolvedTypeName = attribute.resolvedTypeName();
		if (resolvedTypeName == null) {
			return null;
		}
		IPersistentType persistentType = defaultsContext.persistentType(resolvedTypeName);
		if (persistentType != null) {
			if (persistentType.getMapping() instanceof IEmbeddable) {
				return (IEmbeddable) persistentType.getMapping();
			}
		}
		return null;
	}
} // JavaEmbedded
