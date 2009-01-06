/*******************************************************************************
 *  Copyright (c) 2009 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.BooleanTranslator;
import org.eclipse.jpt.core.internal.resource.orm.translators.QueryHintTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class NamedStoredProcedureQueryTranslator extends Translator 
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public NamedStoredProcedureQueryTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createResultClassTranslator(),
			createResultSetMappingTranslator(),
			createProcedureNameTranslator(),
			createReturnResultSetTranslator(),
			createHintTranslator(),
			createParameterTranslator(),
		};
	}

	private Translator createNameTranslator() {
		return new Translator(NAMED_STORED_PROCEDURE_QUERY__NAME, ECLIPSELINK_ORM_PKG.getXmlNamedStoredProcedureQuery_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createResultClassTranslator() {
		return new Translator(NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS, ECLIPSELINK_ORM_PKG.getXmlNamedStoredProcedureQuery_ResultClass(), DOM_ATTRIBUTE);
	}
	
	private Translator createResultSetMappingTranslator() {
		return new Translator(NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING, ECLIPSELINK_ORM_PKG.getXmlNamedStoredProcedureQuery_ResultSetMapping(), DOM_ATTRIBUTE);
	}
	
	private Translator createProcedureNameTranslator() {
		return new Translator(NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME, ECLIPSELINK_ORM_PKG.getXmlNamedStoredProcedureQuery_ProcedureName(), DOM_ATTRIBUTE);
	}
	
	private Translator createReturnResultSetTranslator() {
		return new BooleanTranslator(NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET, ECLIPSELINK_ORM_PKG.getXmlNamedStoredProcedureQuery_ReturnsResultSet(), DOM_ATTRIBUTE);
	}
	
	private Translator createHintTranslator() {
		return new QueryHintTranslator(NAMED_STORED_PROCEDURE_QUERY__HINT, ECLIPSELINK_ORM_PKG.getXmlNamedStoredProcedureQuery_Hints());
	}
	
	private Translator createParameterTranslator() {
		return new StoredProcedureParameterTranslator(NAMED_STORED_PROCEDURE_QUERY__PARAMETER, ECLIPSELINK_ORM_PKG.getXmlNamedStoredProcedureQuery_Parameters());
	}
}
