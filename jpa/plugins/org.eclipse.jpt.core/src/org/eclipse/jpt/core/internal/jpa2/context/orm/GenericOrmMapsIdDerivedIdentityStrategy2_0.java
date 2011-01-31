/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmMapsIdDerivedIdentityStrategy2_0
	extends AbstractOrmXmlContextNode
	implements OrmMapsIdDerivedIdentityStrategy2_0
{
	protected String specifiedValue;
	// no default value


	public GenericOrmMapsIdDerivedIdentityStrategy2_0(OrmDerivedIdentity2_0 parent) {
		super(parent);
		this.specifiedValue = this.getXmlMapping().getMapsId();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedValue_(this.getXmlMapping().getMapsId());
	}


	// ********** value **********

	public String getValue() {
		// there is no default value
		return this.specifiedValue;
	}

	public String getSpecifiedValue() {
		return this.specifiedValue;
	}

	public void setSpecifiedValue(String value) {
		this.setSpecifiedValue_(value);
		this.getXmlMapping().setMapsId(value);
	}

	protected void setSpecifiedValue_(String value) {
		String old = this.specifiedValue;
		this.specifiedValue = value;
		this.firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, value);
	}

	public String getDefaultValue() {
		// there is no way to have default values in xml
		return null;
	}

	public boolean usesDefaultValue() {
		return false;
	}


	// ********** misc **********

	@Override
	public OrmDerivedIdentity2_0 getParent() {
		return (OrmDerivedIdentity2_0) super.getParent();
	}

	protected OrmDerivedIdentity2_0 getDerivedIdentity() {
		return this.getParent();
	}

	public OrmSingleRelationshipMapping2_0 getMapping() {
		return this.getDerivedIdentity().getMapping();
	}

	protected OrmPersistentAttribute getPersistentAttribute() {
		return this.getMapping().getPersistentAttribute();
	}

	protected XmlSingleRelationshipMapping_2_0 getXmlMapping() {
		return this.getMapping().getXmlAttributeMapping();
	}

	protected Iterable<AttributeMapping> getAllAttributeMappings() {
		return CollectionTools.collection(this.getPersistentAttribute().getOwningTypeMapping().allAttributeMappings());
	}

	public Iterable<String> getSortedValueChoices() {
		return CollectionTools.sort(this.getAllAttributeMappingChoiceNames());
	}

	protected Iterable<String> getAllAttributeMappingChoiceNames() {
		return new TransformationIterable<AttributeMapping, String>(this.getAllAttributeMappingChoices()) {
				@Override
				protected String transform(AttributeMapping mapping) {
					return mapping.getName();
				}
			};
	}

	protected Iterable<AttributeMapping> getAllAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(this.getAllAttributeMappings());
	}

	protected Iterable<AttributeMapping> buildAttributeMappingChoices(Iterable<AttributeMapping> attributeMappings) {
		return new CompositeIterable<AttributeMapping>(this.getAttributeMappingChoiceIterables(attributeMappings));
	}

	/**
	 * @see #getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping)
	 */
	protected Iterable<Iterable<AttributeMapping>> getAttributeMappingChoiceIterables(Iterable<AttributeMapping> availableMappings) {
		return new TransformationIterable<AttributeMapping, Iterable<AttributeMapping>>(availableMappings) {
			@Override
			protected Iterable<AttributeMapping> transform(AttributeMapping o) {
				return Tools.valuesAreEqual(o.getKey(), MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) ?
					GenericOrmMapsIdDerivedIdentityStrategy2_0.this.getEmbeddedIdMappingChoiceIterable((EmbeddedIdMapping) o) :
					new SingleElementIterable<AttributeMapping>(o);
			}
		};
	}

	/**
	 * Convert the specified mapping into a collection of its "mappings".
	 * Typically, this collection will include just the mapping itself;
	 * but, if the mapping is an embedded ID, this collection will include
	 * the mapping itself plus all the mappings of its target embeddable.
	 */
	protected Iterable<AttributeMapping> getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping mapping) {
		Embeddable embeddable = mapping.getTargetEmbeddable();
		if (embeddable == null) {
			return new SingleElementIterable<AttributeMapping>(mapping);
		}
		return new CompositeIterable<AttributeMapping>(
				mapping,
				CollectionTools.collection(embeddable.allAttributeMappings())
			);
	}

	public AttributeMapping getResolvedAttributeMappingValue() {
		String value = this.getValue();
		if (value != null) {
			for (AttributeMapping mapping : this.getAllAttributeMappingChoices()) {
				if (value.equals(mapping.getName())) {
					return mapping;
				}
			}
		}
		return null;
	}

	public boolean isSpecified() {
		return this.getXmlMapping().getMapsId() != null;
	}

	public void addStrategy() {
		this.getXmlMapping().setMapsId(""); //$NON-NLS-1$
	}

	public void removeStrategy() {
		this.getXmlMapping().setMapsId(null);
	}

	public void initializeFrom(OrmMapsIdDerivedIdentityStrategy2_0 oldStrategy) {
		this.setSpecifiedValue(oldStrategy.getSpecifiedValue());
	}


	// ********** ID mappings **********

	protected Iterable<AttributeMapping> getIdAttributeMappings() {
		return new FilteringIterable<AttributeMapping>(this.getAllAttributeMappings()) {
			@Override
			protected boolean accept(AttributeMapping mapping) {
				return GenericOrmMapsIdDerivedIdentityStrategy2_0.this.mappingIsIdMapping(mapping);
			}
		};
	}

	protected boolean mappingIsIdMapping(AttributeMapping mapping) {
		return CollectionTools.contains(this.getIdMappingKeys(), mapping.getKey());
	}

	protected Iterable<String> getIdMappingKeys() {
		return ID_MAPPING_KEYS;
	}

	protected static final String[] ID_MAPPING_KEYS_ARRAY = new String[] {
		MappingKeys.ID_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY
	};
	
	protected static final Iterable<String> ID_MAPPING_KEYS = new ArrayIterable<String>(ID_MAPPING_KEYS_ARRAY);


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateMapsId(messages);
	}

	protected void validateMapsId(List<IMessage> messages) {
		if (this.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy()) {
			this.validateMapsId_(messages);
		}
	}

	protected void validateMapsId_(List<IMessage> messages) {
		// test whether value can be resolved
		AttributeMapping attributeMapping = this.getResolvedAttributeMappingValue();
		if (attributeMapping == null) {
			// there is no defaulting, so only use the 'resolved' error, even if the value is empty string
			messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED, new String[] {this.getValue()}));
		} else {
			// test whether attribute mapping is allowable
			if ( ! CollectionTools.contains(this.getValidAttributeMappingChoices(), attributeMapping)) {
				messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_INVALID, new String[] {this.getValue()}));
			}
		}
	}

	protected Iterable<AttributeMapping> getValidAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(this.getIdAttributeMappings());
	}

	protected IMessage buildMessage(String msgID, String[] parms) {
		PersistentAttribute attribute = this.getPersistentAttribute();
		String attributeDescription = attribute.isVirtual() ?
				JpaValidationDescriptionMessages.VIRTUAL_ATTRIBUTE_DESC :
				JpaValidationDescriptionMessages.ATTRIBUTE_DESC;
		attributeDescription = NLS.bind(attributeDescription, attribute.getName());
		parms = ArrayTools.add(parms, 0, attributeDescription);
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				parms,
				this,
				this.getValidationTextRange()
			);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlMapping().getMapsIdTextRange();
		return (textRange != null) ? textRange : this.getDerivedIdentity().getValidationTextRange();
	}
}
