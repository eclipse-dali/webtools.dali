/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne;

public class EclipseLinkOrmVariableOneToOneMapping
	extends AbstractOrmAttributeMapping<XmlVariableOneToOne> 
	implements EclipseLinkVariableOneToOneMapping
{
	public EclipseLinkOrmVariableOneToOneMapping(OrmSpecifiedPersistentAttribute parent, XmlVariableOneToOne xmlMapping) {
		super(parent, xmlMapping);
	}

	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 65;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public void addXmlAttributeMappingTo(org.eclipse.jpt.jpa.core.resource.orm.Attributes xmlAttributes) {
		((Attributes) xmlAttributes).getVariableOneToOnes().add(this.xmlAttributeMapping);
	}
	
	public void removeXmlAttributeMappingFrom(org.eclipse.jpt.jpa.core.resource.orm.Attributes xmlAttributes) {
		((Attributes) xmlAttributes).getVariableOneToOnes().remove(this.xmlAttributeMapping);
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.targetInterfaceTouches(pos)) {
			return this.getCandidateTargetInterfaceNames();
		}
		return null;
	}

	protected boolean targetInterfaceTouches(int pos) {
		return this.xmlAttributeMapping.targetInterfaceTouches(pos);
	}
	
	protected Iterable<String> getCandidateTargetInterfaceNames() {
		return JavaProjectTools.getSortedInterfaceNames(getJavaProject());
	}
}
