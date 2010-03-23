/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmMapsIdDerivedIdentityStrategy2_0
	extends AbstractOrmXmlContextNode
	implements OrmMapsIdDerivedIdentityStrategy2_0
{
	protected XmlMapsId_2_0 resource;
	
	protected String value;
	
	
	public GenericOrmMapsIdDerivedIdentityStrategy2_0(
			OrmDerivedIdentity2_0 parent, XmlMapsId_2_0 resource) {
		super(parent);
		this.resource = resource;
		this.value = this.resource.getMapsId();
	}
	
	
	public OrmDerivedIdentity2_0 getDerivedIdentity() {
		return (OrmDerivedIdentity2_0) getParent();
	}
	
	public OrmSingleRelationshipMapping2_0 getMapping() {
		return getDerivedIdentity().getMapping();
	}
	
	public String getSpecifiedValue() {
		return this.value;
	}
	
	public void setSpecifiedValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.resource.setMapsId(this.value);
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setSpecifiedValue_(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, oldValue, newValue);
	}
	
	public boolean usesDefaultValue() {
		return false;
	}
	
	public String getDefaultValue() {
		// there is no way to have default values in xml
		return null;
	}
	
	public String getValue() {
		// there is never a default value
		return this.value;
	}
	
	public Iterable<String> getSortedValueChoices() {
		return CollectionTools.sort(
			new TransformationIterable<AttributeMapping, String>(getAllAttributeMappingChoices()) {
				@Override
				protected String transform(AttributeMapping o) {
					return o.getName();
				}
			});
	}
	
	public Iterable<AttributeMapping> getAllAttributeMappingChoices() {
		return 	new CompositeIterable<AttributeMapping>(
			getAttributeMappingChoiceIterables(
				CollectionTools.collection(getMapping().getPersistentAttribute().getOwningTypeMapping().allAttributeMappings())));
	}
	
	protected Iterable<Iterable<AttributeMapping>> getAttributeMappingChoiceIterables(Iterable<AttributeMapping> availableMappings) {
		return new TransformationIterable<AttributeMapping, Iterable<AttributeMapping>>(availableMappings) {
			@Override
			protected Iterable<AttributeMapping> transform(AttributeMapping o) {
				if (StringTools.stringsAreEqual(o.getKey(), MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY)) {
					return getEmbeddedIdMappingChoiceIterable((EmbeddedIdMapping) o);
				}
				else {
					return new SingleElementIterable(o);
				}
			}
		};
	}
	
	protected Iterable<AttributeMapping> getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping mapping) {
		Embeddable embeddable = mapping.getTargetEmbeddable();
		if (embeddable == null) {
			return new SingleElementIterable(mapping);
		}
		else {
			return new CompositeIterable<AttributeMapping>(
					mapping,
					CollectionTools.collection(embeddable.allAttributeMappings()));
		}		
	}
	
	public AttributeMapping getResolvedAttributeMappingValue() {
		if (getValue() != null) {
			for (AttributeMapping each : getAllAttributeMappingChoices()) {
				if (each.getName().equals(getValue())) {
					return each;
				}
			}
		}
		return null;
	}
	
	public boolean isSpecified() {
		return this.resource.getMapsId() != null;
	}
	
	public void addStrategy() {
		this.resource.setMapsId("");	
	}
	
	public void removeStrategy() {
		this.resource.setMapsId(null);
	}
	
	public void update() {
		setSpecifiedValue_(this.resource.getMapsId());
	}
	
	public void initializeFrom(OrmMapsIdDerivedIdentityStrategy2_0 oldStrategy) {
		setSpecifiedValue(oldStrategy.getSpecifiedValue());
	}
	
	public TextRange getValidationTextRange() {
		return this.resource.getMapsIdTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateMapsId(messages, reporter);
	}
	
	protected void validateMapsId(List<IMessage> messages, IReporter reporter) {
		// shortcut out if maps id is not even used
		if (! getDerivedIdentity().usesMapsIdDerivedIdentityStrategy()) {
			return;
		}
		
		// test whether value can be resolved
		AttributeMapping attributeMappingValue = getResolvedAttributeMappingValue();
		if (attributeMappingValue == null) {
			// there is no defaulting, so only use the 'resolved' error, even if the value is empty string
			messages.add(buildMessage(JpaValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED, new String[] {getValue()}));
		}
		
		// test whether attribute mapping is allowable
		if (attributeMappingValue != null) {
			if (! CollectionTools.contains(getValidAttributeMappingChoices(), attributeMappingValue)) {
				messages.add(buildMessage(JpaValidationMessages.MAPS_ID_VALUE_INVALID, new String[] {getValue()}));
			}
		}
	}
	
	protected Iterable<AttributeMapping> getValidAttributeMappingChoices() {
		return 	new CompositeIterable<AttributeMapping>(
			getAttributeMappingChoiceIterables(
				new FilteringIterable<AttributeMapping>(
						CollectionTools.collection(getMapping().getPersistentAttribute().getOwningTypeMapping().allAttributeMappings())) {
					@Override
					protected boolean accept(AttributeMapping o) {
						return StringTools.stringsAreEqual(o.getKey(), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY)
							|| StringTools.stringsAreEqual(o.getKey(), MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
					}
				}));
	}
	
	protected IMessage buildMessage(String msgID, String[] params) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY, msgID, params, this, getValidationTextRange());
	}
}
