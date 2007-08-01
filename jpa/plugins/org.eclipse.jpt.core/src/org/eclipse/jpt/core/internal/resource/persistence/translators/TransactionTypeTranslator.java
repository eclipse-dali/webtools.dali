/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.persistence.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnitTransactionType;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class TransactionTypeTranslator extends Translator
	implements PersistenceXMLMapper
{
	public TransactionTypeTranslator() {
		super(TRANSACTION_TYPE, PersistencePackage.eINSTANCE.getPersistenceUnit_TransactionType());
	}
	
	public Object convertStringToValue(String strValue, EObject owner) {
		String adjStrValue = strValue;
		String jtaStrValue = PersistenceUnitTransactionType.JTA.getName();
		String resourceLocalStrValue = PersistenceUnitTransactionType.RESOURCE_LOCAL.getName();
		
		if (jtaStrValue.equals(strValue.toUpperCase())) {  //$NON-NLS-1$
			adjStrValue = jtaStrValue;
		}
		else if (resourceLocalStrValue.equals(strValue.toUpperCase())) {  //$NON-NLS-1$
			adjStrValue = resourceLocalStrValue;
		}
		return super.convertStringToValue(adjStrValue, owner);
	}
}
