/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage
 * @generated
 */
public class OxmValidator extends EObjectValidator
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OxmValidator INSTANCE = new OxmValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OxmValidator()
	{
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage()
	{
	  return OxmPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		switch (classifierID)
		{
			case OxmPackage.EABSTRACT_TYPE_MAPPING:
				return validateEAbstractTypeMapping((EAbstractTypeMapping)value, diagnostics, context);
			case OxmPackage.EABSTRACT_XML_NULL_POLICY:
				return validateEAbstractXmlNullPolicy((EAbstractXmlNullPolicy)value, diagnostics, context);
			case OxmPackage.EABSTRACT_XML_TRANSFORMER:
				return validateEAbstractXmlTransformer((EAbstractXmlTransformer)value, diagnostics, context);
			case OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE:
				return validateEAccessibleJavaAttribute((EAccessibleJavaAttribute)value, diagnostics, context);
			case OxmPackage.EADAPTABLE_JAVA_ATTRIBUTE:
				return validateEAdaptableJavaAttribute((EAdaptableJavaAttribute)value, diagnostics, context);
			case OxmPackage.ECONTAINER_JAVA_ATTRIBUTE:
				return validateEContainerJavaAttribute((EContainerJavaAttribute)value, diagnostics, context);
			case OxmPackage.EJAVA_ATTRIBUTE:
				return validateEJavaAttribute((EJavaAttribute)value, diagnostics, context);
			case OxmPackage.EJAVA_TYPE:
				return validateEJavaType((EJavaType)value, diagnostics, context);
			case OxmPackage.EPROPERTY_HOLDER:
				return validateEPropertyHolder((EPropertyHolder)value, diagnostics, context);
			case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE:
				return validateEReadWriteJavaAttribute((EReadWriteJavaAttribute)value, diagnostics, context);
			case OxmPackage.ETYPED_JAVA_ATTRIBUTE:
				return validateETypedJavaAttribute((ETypedJavaAttribute)value, diagnostics, context);
			case OxmPackage.EXML_ACCESS_METHODS:
				return validateEXmlAccessMethods((EXmlAccessMethods)value, diagnostics, context);
			case OxmPackage.EXML_ANY_ATTRIBUTE:
				return validateEXmlAnyAttribute((EXmlAnyAttribute)value, diagnostics, context);
			case OxmPackage.EXML_ANY_ELEMENT:
				return validateEXmlAnyElement((EXmlAnyElement)value, diagnostics, context);
			case OxmPackage.EXML_ATTRIBUTE:
				return validateEXmlAttribute((EXmlAttribute)value, diagnostics, context);
			case OxmPackage.EXML_BINDINGS:
				return validateEXmlBindings((EXmlBindings)value, diagnostics, context);
			case OxmPackage.EXML_CLASS_EXTRACTOR:
				return validateEXmlClassExtractor((EXmlClassExtractor)value, diagnostics, context);
			case OxmPackage.EXML_ELEMENT:
				return validateEXmlElement((EXmlElement)value, diagnostics, context);
			case OxmPackage.EXML_ELEMENT_DECL:
				return validateEXmlElementDecl((EXmlElementDecl)value, diagnostics, context);
			case OxmPackage.EXML_ELEMENT_REF:
				return validateEXmlElementRef((EXmlElementRef)value, diagnostics, context);
			case OxmPackage.EXML_ELEMENT_REFS:
				return validateEXmlElementRefs((EXmlElementRefs)value, diagnostics, context);
			case OxmPackage.EXML_ELEMENTS:
				return validateEXmlElements((EXmlElements)value, diagnostics, context);
			case OxmPackage.EXML_ELEMENT_WRAPPER:
				return validateEXmlElementWrapper((EXmlElementWrapper)value, diagnostics, context);
			case OxmPackage.EXML_ENUM:
				return validateEXmlEnum((EXmlEnum)value, diagnostics, context);
			case OxmPackage.EXML_ENUM_VALUE:
				return validateEXmlEnumValue((EXmlEnumValue)value, diagnostics, context);
			case OxmPackage.EXML_INVERSE_REFERENCE:
				return validateEXmlInverseReference((EXmlInverseReference)value, diagnostics, context);
			case OxmPackage.EXML_IS_SET_NULL_POLICY:
				return validateEXmlIsSetNullPolicy((EXmlIsSetNullPolicy)value, diagnostics, context);
			case OxmPackage.EXML_IS_SET_PARAMETER:
				return validateEXmlIsSetParameter((EXmlIsSetParameter)value, diagnostics, context);
			case OxmPackage.EXML_JAVA_TYPE_ADAPTER:
				return validateEXmlJavaTypeAdapter((EXmlJavaTypeAdapter)value, diagnostics, context);
			case OxmPackage.EXML_JOIN_NODE:
				return validateEXmlJoinNode((EXmlJoinNode)value, diagnostics, context);
			case OxmPackage.EXML_JOIN_NODES:
				return validateEXmlJoinNodes((EXmlJoinNodes)value, diagnostics, context);
			case OxmPackage.EXML_MAP:
				return validateEXmlMap((EXmlMap)value, diagnostics, context);
			case OxmPackage.EXML_NULL_POLICY:
				return validateEXmlNullPolicy((EXmlNullPolicy)value, diagnostics, context);
			case OxmPackage.EXML_NS:
				return validateEXmlNs((EXmlNs)value, diagnostics, context);
			case OxmPackage.EXML_PROPERTY:
				return validateEXmlProperty((EXmlProperty)value, diagnostics, context);
			case OxmPackage.EXML_READ_TRANSFORMER:
				return validateEXmlReadTransformer((EXmlReadTransformer)value, diagnostics, context);
			case OxmPackage.EXML_REGISTRY:
				return validateEXmlRegistry((EXmlRegistry)value, diagnostics, context);
			case OxmPackage.EXML_ROOT_ELEMENT:
				return validateEXmlRootElement((EXmlRootElement)value, diagnostics, context);
			case OxmPackage.EXML_SCHEMA:
				return validateEXmlSchema((EXmlSchema)value, diagnostics, context);
			case OxmPackage.EXML_SCHEMA_TYPE:
				return validateEXmlSchemaType((EXmlSchemaType)value, diagnostics, context);
			case OxmPackage.EXML_TRANSFORMATION:
				return validateEXmlTransformation((EXmlTransformation)value, diagnostics, context);
			case OxmPackage.EXML_TRANSIENT:
				return validateEXmlTransient((EXmlTransient)value, diagnostics, context);
			case OxmPackage.EXML_TYPE:
				return validateEXmlType((EXmlType)value, diagnostics, context);
			case OxmPackage.EXML_VALUE:
				return validateEXmlValue((EXmlValue)value, diagnostics, context);
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS:
				return validateEXmlVirtualAccessMethods((EXmlVirtualAccessMethods)value, diagnostics, context);
			case OxmPackage.EXML_WRITE_TRANSFORMER:
				return validateEXmlWriteTransformer((EXmlWriteTransformer)value, diagnostics, context);
			case OxmPackage.EXML_ACCESS_ORDER:
				return validateEXmlAccessOrder((EXmlAccessOrder)value, diagnostics, context);
			case OxmPackage.EXML_ACCESS_TYPE:
				return validateEXmlAccessType((EXmlAccessType)value, diagnostics, context);
			case OxmPackage.EXML_MARSHAL_NULL_REPRESENTATION:
				return validateEXmlMarshalNullRepresentation((EXmlMarshalNullRepresentation)value, diagnostics, context);
			case OxmPackage.EXML_NS_FORM:
				return validateEXmlNsForm((EXmlNsForm)value, diagnostics, context);
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS_SCHEMA:
				return validateEXmlVirtualAccessMethodsSchema((EXmlVirtualAccessMethodsSchema)value, diagnostics, context);
			case OxmPackage.EPROP_ORDER:
				return validateEPropOrder((List<?>)value, diagnostics, context);
			case OxmPackage.EXML_SEE_ALSO:
				return validateEXmlSeeAlso((List<?>)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAbstractTypeMapping(EAbstractTypeMapping eAbstractTypeMapping, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eAbstractTypeMapping, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAbstractXmlNullPolicy(EAbstractXmlNullPolicy eAbstractXmlNullPolicy, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eAbstractXmlNullPolicy, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAbstractXmlTransformer(EAbstractXmlTransformer eAbstractXmlTransformer, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eAbstractXmlTransformer, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAccessibleJavaAttribute(EAccessibleJavaAttribute eAccessibleJavaAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eAccessibleJavaAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEAdaptableJavaAttribute(EAdaptableJavaAttribute eAdaptableJavaAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eAdaptableJavaAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEContainerJavaAttribute(EContainerJavaAttribute eContainerJavaAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eContainerJavaAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEJavaAttribute(EJavaAttribute eJavaAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eJavaAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEJavaType(EJavaType eJavaType, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eJavaType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEPropertyHolder(EPropertyHolder ePropertyHolder, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)ePropertyHolder, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEReadWriteJavaAttribute(EReadWriteJavaAttribute eReadWriteJavaAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eReadWriteJavaAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateETypedJavaAttribute(ETypedJavaAttribute eTypedJavaAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eTypedJavaAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlAccessMethods(EXmlAccessMethods eXmlAccessMethods, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlAccessMethods, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlAnyAttribute(EXmlAnyAttribute eXmlAnyAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlAnyAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlAnyElement(EXmlAnyElement eXmlAnyElement, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlAnyElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlAttribute(EXmlAttribute eXmlAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlAttribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlBindings(EXmlBindings eXmlBindings, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlBindings, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlClassExtractor(EXmlClassExtractor eXmlClassExtractor, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlClassExtractor, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlElement(EXmlElement eXmlElement, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlElementDecl(EXmlElementDecl eXmlElementDecl, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlElementDecl, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlElementRef(EXmlElementRef eXmlElementRef, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlElementRef, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlElementRefs(EXmlElementRefs eXmlElementRefs, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlElementRefs, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlElements(EXmlElements eXmlElements, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlElements, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlElementWrapper(EXmlElementWrapper eXmlElementWrapper, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlElementWrapper, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlEnum(EXmlEnum eXmlEnum, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlEnum, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlEnumValue(EXmlEnumValue eXmlEnumValue, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlEnumValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlInverseReference(EXmlInverseReference eXmlInverseReference, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlInverseReference, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlIsSetNullPolicy(EXmlIsSetNullPolicy eXmlIsSetNullPolicy, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlIsSetNullPolicy, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlIsSetParameter(EXmlIsSetParameter eXmlIsSetParameter, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlIsSetParameter, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlJavaTypeAdapter(EXmlJavaTypeAdapter eXmlJavaTypeAdapter, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlJavaTypeAdapter, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlJoinNode(EXmlJoinNode eXmlJoinNode, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlJoinNode, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlJoinNodes(EXmlJoinNodes eXmlJoinNodes, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlJoinNodes, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlMap(EXmlMap eXmlMap, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlMap, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlNullPolicy(EXmlNullPolicy eXmlNullPolicy, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlNullPolicy, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlNs(EXmlNs eXmlNs, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlNs, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlProperty(EXmlProperty eXmlProperty, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlProperty, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlReadTransformer(EXmlReadTransformer eXmlReadTransformer, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlReadTransformer, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlRegistry(EXmlRegistry eXmlRegistry, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlRegistry, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlRootElement(EXmlRootElement eXmlRootElement, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlRootElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlSchema(EXmlSchema eXmlSchema, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlSchema, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlSchemaType(EXmlSchemaType eXmlSchemaType, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlSchemaType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlTransformation(EXmlTransformation eXmlTransformation, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlTransformation, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlTransient(EXmlTransient eXmlTransient, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlTransient, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlType(EXmlType eXmlType, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlValue(EXmlValue eXmlValue, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlVirtualAccessMethods(EXmlVirtualAccessMethods eXmlVirtualAccessMethods, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlVirtualAccessMethods, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlWriteTransformer(EXmlWriteTransformer eXmlWriteTransformer, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint((EObject)eXmlWriteTransformer, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlAccessOrder(EXmlAccessOrder eXmlAccessOrder, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlAccessType(EXmlAccessType eXmlAccessType, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlMarshalNullRepresentation(EXmlMarshalNullRepresentation eXmlMarshalNullRepresentation, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlNsForm(EXmlNsForm eXmlNsForm, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlVirtualAccessMethodsSchema(EXmlVirtualAccessMethodsSchema eXmlVirtualAccessMethodsSchema, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEPropOrder(List<?> ePropOrder, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = validateEPropOrder_ItemType(ePropOrder, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ItemType constraint of '<em>EProp Order</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEPropOrder_ItemType(List<?> ePropOrder, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = true;
		for (Iterator<?> i = ePropOrder.iterator(); i.hasNext() && (result || diagnostics != null); )
		{
			Object item = i.next();
			if (XMLTypePackage.Literals.STRING.isInstance(item))
			{
				result &= xmlTypeValidator.validateString((String)item, diagnostics, context);
			}
			else
			{
				result = false;
				reportDataValueTypeViolation(XMLTypePackage.Literals.STRING, item, diagnostics, context);
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlSeeAlso(List<?> eXmlSeeAlso, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = validateEXmlSeeAlso_ItemType(eXmlSeeAlso, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ItemType constraint of '<em>EXml See Also</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEXmlSeeAlso_ItemType(List<?> eXmlSeeAlso, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = true;
		for (Iterator<?> i = eXmlSeeAlso.iterator(); i.hasNext() && (result || diagnostics != null); )
		{
			Object item = i.next();
			if (XMLTypePackage.Literals.STRING.isInstance(item))
			{
				result &= xmlTypeValidator.validateString((String)item, diagnostics, context);
			}
			else
			{
				result = false;
				reportDataValueTypeViolation(XMLTypePackage.Literals.STRING, item, diagnostics, context);
			}
		}
		return result;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //OxmValidator
