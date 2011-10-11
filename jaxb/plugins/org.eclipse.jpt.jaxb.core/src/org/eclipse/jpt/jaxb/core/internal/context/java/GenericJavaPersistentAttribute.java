/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.Accessor;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPersistentAttribute
		extends AbstractJavaContextNode
		implements JaxbPersistentAttribute {
	
	protected JaxbAttributeMapping mapping;  // never null
	
	protected String defaultMappingKey;
	
	protected final Accessor accessor;
	
	
	public static JaxbPersistentAttribute buildPersistentProperty(
		JaxbClassMapping parent, 
		JavaResourceMethod resourceGetter,
		JavaResourceMethod resourceSetter) {
		return new GenericJavaPersistentAttribute(parent, new PropertyAccessor(parent, resourceGetter, resourceSetter));
	}

	public static JaxbPersistentAttribute buildPersistentField(
		JaxbClassMapping parent, 
		JavaResourceField resourceField) {
		return new GenericJavaPersistentAttribute(parent, new FieldAccessor(parent, resourceField));
	}
	
	
	public GenericJavaPersistentAttribute(JaxbClassMapping parent, Accessor accessor) {
		super(parent);
		this.accessor = accessor;
		// keep non-null at all times
		this.mapping = this.buildMapping();
	}

	public JaxbClassMapping getJaxbClassMapping() {
		return (JaxbClassMapping) super.getParent();
	}

	public boolean isInherited() {
		return getJaxbClassMapping().isInherited(this);
	}

	public String getInheritedJavaResourceAttributeOwningTypeName() {
		return getJaxbClassMapping().getJavaResourceAttributeOwningTypeName(this);
	}
	
	
	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncMapping();
	}

	@Override
	public void update() {
		super.update();
		this.updateMapping();
	}

	// ********** name **********

	public String getName() {
		return this.getJavaResourceAttribute().getName();
	}

	// ********** accessor ********

	public boolean isFor(JavaResourceField resourceField) {
		return this.accessor.isFor(resourceField);
	}

	public boolean isFor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return this.accessor.isFor(resourceGetter, resourceSetter);
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.accessor.getJavaResourceAttribute();
	}
	
	public String getJavaResourceAttributeBaseTypeName() {
		return this.accessor.getJavaResourceAttributeBaseTypeName();
	}
	
	public boolean isJavaResourceAttributeCollectionType() {
		return this.accessor.isJavaResourceAttributeCollectionType();
	}
	
	public boolean isJavaResourceAttributeTypeSubTypeOf(String typeName) {
		return this.accessor.isJavaResourceAttributeTypeSubTypeOf(typeName);
	}
	
	// ********** mapping **********

	public JaxbAttributeMapping getMapping() {
		return this.mapping;
	}

	/**
	 * Clients do not set the mapping directly.
	 * @see #setMappingKey(String)
	 */
	protected void setMapping(JaxbAttributeMapping mapping) {
		JaxbAttributeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(MAPPING_PROPERTY, old, mapping);
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	/**
	 * Possible transitions:
	 * <table border>
	 * <th>
	 * <th>null mapping/default<br>
	 *     <code>key = null</code>
	 * <th>specified mapping A<br>
	 *     <code>key = "A"</code>
	 * <th>specified mapping B<br>
	 *     <code>key = "B"</code>
	 * <tr>
	 * <th>[default] null mapping
	 *   <td>do nothing
	 *   <td>add annotation A<br>
	 *       set new mapping A
	 *   <td>add annotation B<br>
	 *       set new mapping B
	 * <tr>
	 * <th>default mapping A
	 *   <td>do nothing
	 *   <td>add annotation A<br>
	 *       <em>re-use</em> default mapping A
	 *   <td>add annotation B<br>
	 *       set new mapping B
	 * <tr>
	 * <th>specified mapping A
	 *   <td>remove annotation A<br>
	 *       set new default or null mapping
	 *   <td>do nothing
	 *   <td>remove annotation A<br>
	 *       add annotation B<br>
	 *       set new mapping B
	 * </table>
	 * The "do nothing" transitions are handled in this method.
	 */
	public JaxbAttributeMapping setMappingKey(String key) {
		if (this.mapping.isDefault()) {
			if (key == null) {
				// leave the default mapping unchanged
			} else {
				this.setMappingKey_(key);  // replace the default mapping
			}
		} else {
			if (this.valuesAreEqual(key, this.mapping.getKey())) {
				// leave the specified mapping unchanged
			} else {
				this.setMappingKey_(key);  // replace the specified mapping
			}
		}
		return this.mapping;
	}

	/**
	 * We have either:<ul>
	 * <li>a <em>default</em> mapping and a non-<code>null</code> key
	 * </ul>or<ul>
	 * <li>a <em>specified</em> mapping and a different (possibly
	 *     <code>null</code>) key
	 * </ul>
	 */
	protected void setMappingKey_(String key) {
		JavaAttributeMappingDefinition definition = this.getSpecifiedMappingDefinition(key);
		if (definition == null) {
			// our mapping is "specified" and the key is null;
			// check for a default definition
			definition = this.getDefaultMappingDefinition();
			Iterable<String> supportingAnnotationNames = (definition != null) ? definition.getSupportingAnnotationNames() : EmptyIterable.<String>instance();
			// clear any mapping annotation(s);
			// leave the "default" mapping's supporting annotations;
			// if there is no "default" mapping, clear all supporting annotations too(?)
			this.setMappingAnnotation(null, supportingAnnotationNames);
		} else {
			this.setMappingAnnotation(definition);
		}
		// note: 'definition' can still be null (if the key is null and there is no "default" mapping)
		this.setMapping(this.buildMapping(definition));
	}

	/**
	 * pre-condition: definition is not <code>null</code>
	 */
	protected void setMappingAnnotation(JavaAttributeMappingDefinition definition) {
		this.setMappingAnnotation(definition.getAnnotationName(), definition.getSupportingAnnotationNames());
	}

	protected void setMappingAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		this.getJavaResourceAttribute().setPrimaryAnnotation(primaryAnnotationName, supportingAnnotationNames);
	}

	protected JaxbAttributeMapping buildMapping(JavaAttributeMappingDefinition definition) {
		return (definition == null) ? this.buildNullMapping() : this.buildMapping_(definition);
	}

	protected JaxbAttributeMapping buildNullMapping() {
		return this.getFactory().buildJavaNullAttributeMapping(this);
	}

	/**
	 * pre-condition: definition is not null
	 * <p>
	 * If we are converting a <em>default</em> mapping to its <em>specified</em>
	 * manifestation, we just keep the same mapping and create its annotation.
	 * We do <em>not</em> do the same thing when converting a <em>specified</em>
	 * mapping to its <em>default</em> manifestation. We rebuild the
	 * entire mapping, simplifying the clearing of all its state. We do this
	 * because we allow clients to modify a <em>default</em> mapping (or any of
	 * its components) directly,
	 * modifying its state and triggering a conversion to a <em>specified</em>
	 * mapping. The only way to convert a <em>specified</em> mapping to a
	 * <em>default</em> mapping is by {@link #setMappingKey(String) setting the
	 * mapping key} to <code>null</code>.
	 */
	protected JaxbAttributeMapping buildMapping_(JavaAttributeMappingDefinition definition) {
		// 'mapping' is null during construction
		if ((this.mapping != null) && this.mapping.isDefault() && Tools.valuesAreEqual(this.mapping.getKey(), definition.getKey())) {
			this.mapping.synchronizeWithResourceModel();  // the mapping instance hasn't changed, but some resource differences may have resulted
			return this.mapping;
		}
		return definition.buildMapping(this, this.getFactory());
	}

	/**
	 * We only look for a <em>specified</em> mapping here.
	 * We look for a default mapping during <em>update</em>.
	 */
	protected JaxbAttributeMapping buildMapping() {
		return this.buildMapping(this.getSpecifiedMappingDefinition());
	}

	/**
	 * Look for a <em>specified</em> mapping and sync our mapping.
	 */
	protected void syncMapping() {
		JavaAttributeMappingDefinition definition = this.getSpecifiedMappingDefinition();
		if (definition == null) {
			if (this.mapping.isDefault()) {
				// null/default => null/default
				this.mapping.synchronizeWithResourceModel();
			} else {
				// specified => null/default
				definition = this.getDefaultMappingDefinition();
				this.setMapping(this.buildMapping(definition));
			}
		} else {
			if (this.mapping.isDefault()) {
				// null/default => specified
				// (will not change mapping instance if keys are the same, but will sync the mapping)
				this.setMapping(this.buildMapping(definition));
			} else {
				// specified => specified
				if (this.valuesAreEqual(definition.getKey(), this.mapping.getKey())) {
					this.mapping.synchronizeWithResourceModel();
				} else {
					this.setMapping(this.buildMapping(definition));
				}
			}
		}
	}

	/**
	 * Return the "specified" mapping definition for the specified key.
	 */
	protected JavaAttributeMappingDefinition getSpecifiedMappingDefinition(String key) {
		if (key == null) {
			return null;
		}
		for (JavaAttributeMappingDefinition definition : this.getSpecifiedMappingDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), key)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("invalid mapping key: " + key); //$NON-NLS-1$
	}

	/**
	 * Return the mapping definition for the mapping currently specified in the
	 * source code.
	 */
	protected JavaAttributeMappingDefinition getSpecifiedMappingDefinition() {
		for (JavaAttributeMappingDefinition definition : this.getSpecifiedMappingDefinitions()) {
			if (definition.isSpecified(this)) {
				return definition;
			}
		}
		return null;
	}

	protected Iterable<JavaAttributeMappingDefinition> getSpecifiedMappingDefinitions() {
		return this.getPlatform().getSpecifiedJavaAttributeMappingDefinitions();
	}


	// ********** default mapping **********

	public String getDefaultMappingKey() {
		return this.defaultMappingKey;
	}

	protected void setDefaultMappingKey(String mappingKey) {
		String old = this.defaultMappingKey;
		this.defaultMappingKey = mappingKey;
		this.firePropertyChanged(DEFAULT_MAPPING_KEY_PROPERTY, old, mappingKey);
	}

	/**
	 * If a mapping annotation is specified, we would have already set a
	 * <em>specified</em> mapping in {@link #syncMapping()}. We need only check
	 * for changes to the <em>default</em> mapping.
	 */
	protected void updateMapping() {
		JavaAttributeMappingDefinition definition = this.getDefaultMappingDefinition();
		String newDefaultKey = (definition == null) ? null : definition.getKey();
		if (this.mapping.isDefault() && Tools.valuesAreDifferent(this.mapping.getKey(), newDefaultKey)) {
			this.setMapping(this.buildMapping(definition));  // the default mapping has changed
		} else {
			this.mapping.update();
		}
		this.setDefaultMappingKey(newDefaultKey);
	}

	protected JavaAttributeMappingDefinition getDefaultMappingDefinition() {
		for (DefaultJavaAttributeMappingDefinition definition : this.getDefaultMappingDefinitions()) {
			if (definition.isDefault(this)) {
				return definition;
			}
		}
		return null;
	}

	protected Iterable<DefaultJavaAttributeMappingDefinition> getDefaultMappingDefinitions() {
		return this.getPlatform().getDefaultJavaAttributeMappingDefinitions();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}

	// **************** content assist ****************************************

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.mapping.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}


	// **************** validation ********************************************

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getJavaResourceAttribute().getTextRange(astRoot);
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		// validate that unsupported annotations are not present
		JavaAttributeMappingDefinition currentMappingDefinition = getCurrentMappingDefinition();
		Iterable<String> supportingAnnotationNames = currentMappingDefinition.getSupportingAnnotationNames();
		
		for (Annotation annotation : getJavaResourceAttribute().getAnnotations()) {
			if (Tools.valuesAreDifferent(currentMappingDefinition.getAnnotationName(), annotation.getAnnotationName())
					&& ! CollectionTools.contains(supportingAnnotationNames, annotation.getAnnotationName())) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.ATTRIBUTE_MAPPING__UNSUPPORTED_ANNOTATION,
								new String[] { annotation.getAnnotationName(), currentMappingDefinition.getAnnotationName() },
								this,
								annotation.getTextRange(astRoot)));
			}
		}
		
		this.getMapping().validate(messages, reporter, astRoot);
	}
	
	protected JavaAttributeMappingDefinition getCurrentMappingDefinition() {
		Iterable<? extends JavaAttributeMappingDefinition> mappingDefinitions = 
				(this.mapping.isDefault()) ? 
						getDefaultMappingDefinitions()
						: getSpecifiedMappingDefinitions();
		
		for (JavaAttributeMappingDefinition mappingDefinition : mappingDefinitions) {
			if (Tools.valuesAreEqual(mappingDefinition.getKey(), this.mapping.getKey())) {
				return mappingDefinition;
			}
		}
		
		return null;
	}
}
