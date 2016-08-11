/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * EclipseLink Customization
 */
public interface EclipseLinkCustomization
	extends PersistenceUnitProperties
{
	Boolean getThrowExceptions();
	void setThrowExceptions(Boolean newThrowExceptions);
		String THROW_EXCEPTIONS_PROPERTY = "throwExceptions"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_THROW_EXCEPTIONS = "eclipselink.orm.throw.exceptions"; //$NON-NLS-1$
	Boolean getDefaultThrowExceptions();
		String DEFAULT_THROW_EXCEPTIONS_PROPERTY = "defaultThrowExceptions"; //$NON-NLS-1$
		Boolean DEFAULT_THROW_EXCEPTIONS = Boolean.TRUE;

	EclipseLinkWeaving getWeaving();
	void setWeaving(EclipseLinkWeaving newWeaving);
		String WEAVING_PROPERTY = "weaving"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WEAVING = "eclipselink.weaving"; //$NON-NLS-1$
	EclipseLinkWeaving getDefaultWeaving();
		String DEFAULT_WEAVING_PROPERTY = "defaultWeaving"; //$NON-NLS-1$
		EclipseLinkWeaving DEFAULT_WEAVING = EclipseLinkWeaving.true_;
 
	Boolean getWeavingLazy();
	void setWeavingLazy(Boolean newWeavingLazy);
		String WEAVING_LAZY_PROPERTY = "weavingLazy"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WEAVING_LAZY = "eclipselink.weaving.lazy"; //$NON-NLS-1$
	Boolean getDefaultWeavingLazy();
		String DEFAULT_WEAVING_LAZY_PROPERTY = "defaultWeavingLazy"; //$NON-NLS-1$
		Boolean DEFAULT_WEAVING_LAZY = Boolean.TRUE;

	Boolean getWeavingChangeTracking();
	void setWeavingChangeTracking(Boolean newWeavingChangeTracking);
		String WEAVING_CHANGE_TRACKING_PROPERTY = "weavingChangeTracking"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WEAVING_CHANGE_TRACKING = "eclipselink.weaving.changetracking"; //$NON-NLS-1$
	Boolean getDefaultWeavingChangeTracking();
		String DEFAULT_WEAVING_CHANGE_TRACKING_PROPERTY = "defaultWeavingChangeTracking"; //$NON-NLS-1$
		Boolean DEFAULT_WEAVING_CHANGE_TRACKING = Boolean.TRUE;

	Boolean getWeavingFetchGroups();
	void setWeavingFetchGroups(Boolean newWeavingFetchGroups);
		String WEAVING_FETCH_GROUPS_PROPERTY = "weavingFetchGroups"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WEAVING_FETCH_GROUPS = "eclipselink.weaving.fetchgroups"; //$NON-NLS-1$
	Boolean getDefaultWeavingFetchGroups();
		String DEFAULT_WEAVING_FETCH_GROUPS_PROPERTY = "defaultWeavingFetchGroups"; //$NON-NLS-1$
		Boolean DEFAULT_WEAVING_FETCH_GROUPS = Boolean.TRUE;

	Boolean getWeavingInternal();
	void setWeavingInternal(Boolean newWeavingInternal);
		String WEAVING_INTERNAL_PROPERTY = "weavingInternal"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WEAVING_INTERNAL = "eclipselink.weaving.internal"; //$NON-NLS-1$
	Boolean getDefaultWeavingInternal();
		String DEFAULT_WEAVING_INTERNAL_PROPERTY = "defaultWeavingInternal"; //$NON-NLS-1$
		Boolean DEFAULT_WEAVING_INTERNAL = Boolean.TRUE;

	Boolean getWeavingEager();
	void setWeavingEager(Boolean newWeavingEager);
		String WEAVING_EAGER_PROPERTY = "weavingEager"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WEAVING_EAGER = "eclipselink.weaving.eager"; //$NON-NLS-1$
	Boolean getDefaultWeavingEager();
		String DEFAULT_WEAVING_EAGER_PROPERTY = "defaultWeavingEager"; //$NON-NLS-1$
		Boolean DEFAULT_WEAVING_EAGER = Boolean.FALSE;

	String getDescriptorCustomizerOf(String entityName);
	void setDescriptorCustomizerOf(String entityName, String newDescriptorCustomizer);
		String DESCRIPTOR_CUSTOMIZER_PROPERTY = "descriptorCustomizer"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_DESCRIPTOR_CUSTOMIZER = "eclipselink.descriptor.customizer."; //$NON-NLS-1$
	String getDefaultDescriptorCustomizer();
		String DEFAULT_DESCRIPTOR_CUSTOMIZER_PROPERTY = "defaultDescriptorCustomizer"; //$NON-NLS-1$
		String DEFAULT_DESCRIPTOR_CUSTOMIZER = null;	// no default

	ListIterable<String> getSessionCustomizers();
	int getSessionCustomizersSize();
	boolean sessionCustomizerExists(String sessionCustomizerClassName);
	String addSessionCustomizer(String newSessionCustomizerClassName);
	void removeSessionCustomizer(String sessionCustomizerClassName);
		String SESSION_CUSTOMIZER_LIST = "sessionCustomizers"; //$NON-NLS-1$
		String SESSION_CUSTOMIZER_PROPERTY = "sessionCustomizer"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_SESSION_CUSTOMIZER = "eclipselink.session.customizer"; //$NON-NLS-1$
		String ECLIPSELINK_SESSION_CUSTOMIZER_CLASS_NAME = "org.eclipse.persistence.config.SessionCustomizer"; //$NON-NLS-1$

	ListIterable<EclipseLinkCustomizationEntity> getEntities();
	Iterable<String> getEntityNames();
	int getEntitiesSize();
	boolean entityExists(String entity);
	EclipseLinkCustomizationEntity addEntity(String entity);
	void removeEntity(String entity);
		String ENTITIES_LIST = "entities"; //$NON-NLS-1$
		
	String getProfiler();
	void setProfiler(String newProfiler);
	void setProfiler(EclipseLinkProfiler newProfiler);
		String PROFILER_PROPERTY = "profiler"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_PROFILER = "eclipselink.profiler"; //$NON-NLS-1$
	String getDefaultProfiler();
		String DEFAULT_PROFILER_PROPERTY = "defaultProfiler"; //$NON-NLS-1$
		String DEFAULT_PROFILER = EclipseLinkProfiler.no_profiler.getPropertyValue();
		String[] RESERVED_PROFILER_NAMES = {EclipseLinkProfiler.no_profiler.getPropertyValue(), EclipseLinkProfiler.performance_profiler.getPropertyValue(), EclipseLinkProfiler.query_monitor.getPropertyValue()};
		String ECLIPSELINK_SESSION_PROFILER_CLASS_NAME = "org.eclipse.persistence.sessions.SessionProfiler"; //$NON-NLS-1$

	Boolean getValidationOnly();
	void setValidationOnly(Boolean newValidationOnly);
		String VALIDATION_ONLY_PROPERTY = "validationOnly"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_VALIDATION_ONLY = "eclipselink.validation-only"; //$NON-NLS-1$
	Boolean getDefaultValidationOnly();
		String DEFAULT_VALIDATION_ONLY_PROPERTY = "defaultValidationOnly"; //$NON-NLS-1$
		Boolean DEFAULT_VALIDATION_ONLY = Boolean.TRUE;

	String getExceptionHandler();
	void setExceptionHandler(String newExceptionHandler);
		String EXCEPTION_HANDLER_PROPERTY = "exceptionHandler"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_EXCEPTION_HANDLER = "eclipselink.exception-handler"; //$NON-NLS-1$
	String getDefaultExceptionHandler();
		String DEFAULT_EXCEPTION_HANDLER_PROPERTY = "defaultExceptionHandler"; //$NON-NLS-1$
		String DEFAULT_EXCEPTION_HANDLER = null;	// no default
		String ECLIPSELINK_EXCEPTION_HANDLER_CLASS_NAME = "org.eclipse.persistence.exceptions.ExceptionHandler"; //$NON-NLS-1$

	Boolean getValidateSchema();
	void setValidateSchema(Boolean newValidateSchema);
		String VALIDATE_SCHEMA_PROPERTY = "validateSchema"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_VALIDATE_SCHEMA = "eclipselink.orm.validate.schema"; //$NON-NLS-1$
	Boolean getDefaultValidateSchema();
		String DEFAULT_VALIDATE_SCHEMA_PROPERTY = "defaultValidateSchema"; //$NON-NLS-1$
		Boolean DEFAULT_VALIDATE_SCHEMA = Boolean.FALSE;
}
