/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaEObject;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.IXmlEObject;
import org.eclipse.jpt.core.internal.JpaEObject;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.persistence.JavaClassRef;
import org.eclipse.jpt.core.internal.content.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.content.persistence.Persistence;
import org.eclipse.jpt.core.internal.content.persistence.PersistencePackage;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlRootContentNode;
import org.eclipse.jpt.core.internal.content.persistence.Properties;
import org.eclipse.jpt.core.internal.content.persistence.Property;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage
 * @generated
 */
public class PersistenceSwitch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PersistencePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceSwitch() {
		if (modelPackage == null) {
			modelPackage = PersistencePackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE : {
				PersistenceXmlRootContentNode persistenceXmlRootContentNode = (PersistenceXmlRootContentNode) theEObject;
				T result = casePersistenceXmlRootContentNode(persistenceXmlRootContentNode);
				if (result == null)
					result = caseXmlEObject(persistenceXmlRootContentNode);
				if (result == null)
					result = caseIJpaRootContentNode(persistenceXmlRootContentNode);
				if (result == null)
					result = caseJpaEObject(persistenceXmlRootContentNode);
				if (result == null)
					result = caseIXmlEObject(persistenceXmlRootContentNode);
				if (result == null)
					result = caseIJpaContentNode(persistenceXmlRootContentNode);
				if (result == null)
					result = caseIJpaEObject(persistenceXmlRootContentNode);
				if (result == null)
					result = caseIJpaSourceObject(persistenceXmlRootContentNode);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PersistencePackage.PERSISTENCE : {
				Persistence persistence = (Persistence) theEObject;
				T result = casePersistence(persistence);
				if (result == null)
					result = caseXmlEObject(persistence);
				if (result == null)
					result = caseJpaEObject(persistence);
				if (result == null)
					result = caseIXmlEObject(persistence);
				if (result == null)
					result = caseIJpaEObject(persistence);
				if (result == null)
					result = caseIJpaSourceObject(persistence);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PersistencePackage.PERSISTENCE_UNIT : {
				PersistenceUnit persistenceUnit = (PersistenceUnit) theEObject;
				T result = casePersistenceUnit(persistenceUnit);
				if (result == null)
					result = caseXmlEObject(persistenceUnit);
				if (result == null)
					result = caseJpaEObject(persistenceUnit);
				if (result == null)
					result = caseIXmlEObject(persistenceUnit);
				if (result == null)
					result = caseIJpaEObject(persistenceUnit);
				if (result == null)
					result = caseIJpaSourceObject(persistenceUnit);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PersistencePackage.MAPPING_FILE_REF : {
				MappingFileRef mappingFileRef = (MappingFileRef) theEObject;
				T result = caseMappingFileRef(mappingFileRef);
				if (result == null)
					result = caseXmlEObject(mappingFileRef);
				if (result == null)
					result = caseJpaEObject(mappingFileRef);
				if (result == null)
					result = caseIXmlEObject(mappingFileRef);
				if (result == null)
					result = caseIJpaEObject(mappingFileRef);
				if (result == null)
					result = caseIJpaSourceObject(mappingFileRef);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PersistencePackage.JAVA_CLASS_REF : {
				JavaClassRef javaClassRef = (JavaClassRef) theEObject;
				T result = caseJavaClassRef(javaClassRef);
				if (result == null)
					result = caseXmlEObject(javaClassRef);
				if (result == null)
					result = caseJpaEObject(javaClassRef);
				if (result == null)
					result = caseIXmlEObject(javaClassRef);
				if (result == null)
					result = caseIJpaEObject(javaClassRef);
				if (result == null)
					result = caseIJpaSourceObject(javaClassRef);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PersistencePackage.PROPERTIES : {
				Properties properties = (Properties) theEObject;
				T result = caseProperties(properties);
				if (result == null)
					result = caseXmlEObject(properties);
				if (result == null)
					result = caseJpaEObject(properties);
				if (result == null)
					result = caseIXmlEObject(properties);
				if (result == null)
					result = caseIJpaEObject(properties);
				if (result == null)
					result = caseIJpaSourceObject(properties);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PersistencePackage.PROPERTY : {
				Property property = (Property) theEObject;
				T result = caseProperty(property);
				if (result == null)
					result = caseXmlEObject(property);
				if (result == null)
					result = caseJpaEObject(property);
				if (result == null)
					result = caseIXmlEObject(property);
				if (result == null)
					result = caseIJpaEObject(property);
				if (result == null)
					result = caseIJpaSourceObject(property);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default :
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Persistence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Persistence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistence(Persistence object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceUnit(PersistenceUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping File Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping File Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappingFileRef(MappingFileRef object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Java Class Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Java Class Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaClassRef(JavaClassRef object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperties(Properties object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaEObject(IJpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Jpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Jpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaEObject(JpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa Source Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa Source Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaSourceObject(IJpaSourceObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IXml EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IXml EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIXmlEObject(IXmlEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Xml Root Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Xml Root Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceXmlRootContentNode(PersistenceXmlRootContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Xml EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Xml EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEObject(XmlEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaContentNode(IJpaContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaRootContentNode(IJpaRootContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}
} //PersistenceInternalSwitch
