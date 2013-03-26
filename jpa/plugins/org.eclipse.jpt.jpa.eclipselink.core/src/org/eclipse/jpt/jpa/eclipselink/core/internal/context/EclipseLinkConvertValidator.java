/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkConvertValidator
	implements JpaValidator
{
	protected final EclipseLinkConvert convert;


	public EclipseLinkConvertValidator(EclipseLinkConvert convert) {
		super();
		this.convert = convert;
	}

	protected AttributeMapping getAttributeMapping() {
		return this.convert.getParent();
	}

	protected EclipseLinkPersistenceUnit getPersistenceUnit() {
		return this.convert.getPersistenceUnit();
	}

	/**
	 * The converters are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit#validateConverters(List, IReporter)
	 */
	public boolean validate(List<IMessage> messages, IReporter reporter) {
		// converters are validated in the persistence unit
		return this.validateConverterName(messages);
	}

	
	private boolean validateConverterName(List<IMessage> messages) {
		String converterName = this.convert.getConverterName();
		if (converterName == null) {
			return true;
		}

		if (IterableTools.contains(this.getPersistenceUnit().getUniqueConverterNames(), converterName)) {
			return true;
		}

		if (ArrayTools.contains(EclipseLinkConvert.RESERVED_CONVERTER_NAMES, converterName)) {
			return true;
		}

		messages.add(
			ValidationMessageTools.buildValidationMessage(
				this.getAttributeMapping().getResource(),
				this.getValidationTextRange(),
				JptJpaEclipseLinkCoreValidationMessages.ID_MAPPING_UNRESOLVED_CONVERTER_NAME,
				converterName,
				this.getAttributeMapping().getName()
			)
		);
		return false;
	}

	protected TextRange getValidationTextRange() {
		return this.getAttributeMapping().getPersistentAttribute().isVirtual() ?
				this.getVirtualValidationTextRange() :
				this.convert.getValidationTextRange();		
	}

	protected TextRange getVirtualValidationTextRange() {
		return getAttributeMapping().getPersistentAttribute().getValidationTextRange();
	}

}
