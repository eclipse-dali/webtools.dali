/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/*
 * Currently, it does not look as though default values (maps-id="")
 * are supported in XML.  Therefore there is only a specified value
 * and the alternative is an error specifying the value.
 */
public class GenericOrmMapsIdDerivedIdentityStrategy2_0
		extends AbstractOrmXmlContextModel<OrmDerivedIdentity2_0>
		implements OrmMapsIdDerivedIdentityStrategy2_0 {
	
	protected String specifiedIdAttributeName;
	
	
	public GenericOrmMapsIdDerivedIdentityStrategy2_0(OrmDerivedIdentity2_0 parent) {
		super(parent);
		this.specifiedIdAttributeName = buildSpecifiedIdAttributeName();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedIdAttributeName_(this.buildSpecifiedIdAttributeName());
	}
	
	
	// ********** ID attribute name **********
	
	public String getIdAttributeName() {
		return this.specifiedIdAttributeName;
	}
	
	public String getSpecifiedIdAttributeName() {
		return this.specifiedIdAttributeName;
	}
	
	public void setSpecifiedIdAttributeName(String idAttributeName) {
		this.setSpecifiedIdAttributeName_(idAttributeName);
		this.getXmlMapping().setMapsId(idAttributeName);
	}
	
	protected void setSpecifiedIdAttributeName_(String idAttributeName) {
		String old = this.specifiedIdAttributeName;
		this.specifiedIdAttributeName = idAttributeName;
		this.firePropertyChanged(SPECIFIED_ID_ATTRIBUTE_NAME_PROPERTY, old, idAttributeName);
	}
	
	protected String buildSpecifiedIdAttributeName() {
		return getXmlMapping().getMapsId();
	}
	
	public String getDefaultIdAttributeName() {
		// see class comment
		return null;
	}
	
	public boolean defaultIdAttributeNameIsPossible() {
		// see class comment
		return false;
	}
	
	
	// ********** misc **********
	
	protected OrmDerivedIdentity2_0 getDerivedIdentity() {
		return this.parent;
	}
	
	public OrmSingleRelationshipMapping2_0 getMapping() {
		return this.getDerivedIdentity().getMapping();
	}
	
	protected OrmSpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getMapping().getPersistentAttribute();
	}
	
	protected XmlSingleRelationshipMapping_2_0 getXmlMapping() {
		return this.getMapping().getXmlAttributeMapping();
	}
	
	protected Iterable<AttributeMapping> getAllAttributeMappings() {
		return this.getPersistentAttribute().getDeclaringTypeMapping().getAllAttributeMappings();
	}
	
	public Iterable<String> getSortedCandidateIdAttributeNames() {
		return IterableTools.sort(this.getNonNullCandidateIdAttributeNames());
	}
	
	protected Iterable<String> getNonNullCandidateIdAttributeNames() {
		return IterableTools.removeNulls(this.getCandidateIdAttributeNames());
	}
	
	protected Iterable<String> getCandidateIdAttributeNames() {
		return IterableTools.transform(this.getCandidateIdAttributeMappings(), AttributeMapping.NAME_TRANSFORMER);
	}
	
	protected Iterable<AttributeMapping> getCandidateIdAttributeMappings() {
		return this.buildCandidateIdAttributeMappings(this.getAllAttributeMappings());
	}
	
	protected Iterable<AttributeMapping> buildCandidateIdAttributeMappings(Iterable<AttributeMapping> attributeMappings) {
		return IterableTools.children(attributeMappings, CANDIDATE_ID_ATTRIBUTE_MAPPING_LISTS_TRANSFORMER);
	}
	
	protected static final Transformer<AttributeMapping, Iterable<AttributeMapping>> CANDIDATE_ID_ATTRIBUTE_MAPPING_LISTS_TRANSFORMER = new CandidateIdAttributeMappingListsTransformer();
	
	protected static class CandidateIdAttributeMappingListsTransformer
			extends TransformerAdapter<AttributeMapping, Iterable<AttributeMapping>> {
		
		@Override
		public Iterable<AttributeMapping> transform(AttributeMapping attributeMapping) {
			return ObjectTools.equals(attributeMapping.getKey(), MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) ?
					this.getEmbeddedIdMappingChoiceIterable((EmbeddedIdMapping) attributeMapping) :
					new SingleElementIterable<AttributeMapping>(attributeMapping);
		}
		
		/**
		 * Convert the specified mapping into a collection of its "mappings".
		 * Typically, this collection will include just the mapping itself;
		 * but, if the mapping is an embedded ID, this collection will include
		 * the mapping itself plus all the mappings of its target embeddable.
		 * <p>
		 * <strong>NB:</strong> Recursion is unnecessary.
		 */
		protected Iterable<AttributeMapping> getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping mapping) {
			Embeddable embeddable = mapping.getTargetEmbeddable();
			return (embeddable == null) ?
					IterableTools.<AttributeMapping>singletonIterable(mapping) :
					IterableTools.insert(mapping, embeddable.getAllAttributeMappings());
		}
	}
	
	public AttributeMapping getDerivedIdAttributeMapping() {
		String idAttributeName = this.getIdAttributeName();
		if (idAttributeName != null) {
			for (AttributeMapping mapping : this.getCandidateIdAttributeMappings()) {
				if (idAttributeName.equals(mapping.getName())) {
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
		this.setSpecifiedIdAttributeName(oldStrategy.getSpecifiedIdAttributeName());
	}
	
	
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
		// test whether id attribute name can be resolved
		AttributeMapping attributeMapping = this.getDerivedIdAttributeMapping();
		if (attributeMapping == null) {
			// there is no defaulting, so only use the 'resolved' error, even if the name is an empty string
			messages.add(this.buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED, new String[] {this.getIdAttributeName()}));
		} else {
			// test whether attribute mapping is allowable
			if ( ! IterableTools.contains(this.getValidAttributeMappingChoices(), attributeMapping)) {
				messages.add(this.buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_INVALID, new String[] {this.getIdAttributeName()}));
			}
		}
	}
	
	protected Iterable<AttributeMapping> getValidAttributeMappingChoices() {
		return this.buildCandidateIdAttributeMappings(getPersistentAttribute().getDeclaringTypeMapping().getIdAttributeMappings());
	}
	
	protected IMessage buildMessage(ValidationMessage msg, Object[] args) {
		String attributeDescription = NLS.bind(JptJpaCoreValidationArgumentMessages.ATTRIBUTE_DESC, this.getPersistentAttribute().getName());
		args = ArrayTools.add(args, 0, attributeDescription);
		return this.buildValidationMessage(this.getValidationTextRange(), msg, args);
	}
	
	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlMapping().getMapsIdTextRange();
		return (textRange != null) ? textRange : this.getDerivedIdentity().getValidationTextRange();
	}
}
