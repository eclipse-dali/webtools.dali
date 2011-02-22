/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnitProperties;

/**
 *  Customization
 */
public interface Customization extends PersistenceUnitProperties
{
	Boolean getDefaultThrowExceptions();
	Boolean getThrowExceptions();
	void setThrowExceptions(Boolean newThrowExceptions);
		static final String THROW_EXCEPTIONS_PROPERTY = "throwExceptions"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_THROW_EXCEPTIONS = "eclipselink.orm.throw.exceptions"; //$NON-NLS-1$
		static final Boolean DEFAULT_THROW_EXCEPTIONS = Boolean.TRUE;
	
	Weaving getDefaultWeaving();
	Weaving getWeaving();
	void setWeaving(Weaving newWeaving);
		static final String WEAVING_PROPERTY = "weaving"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING = "eclipselink.weaving"; //$NON-NLS-1$
		static final Weaving DEFAULT_WEAVING = Weaving.true_;
 
	Boolean getDefaultWeavingLazy();
	Boolean getWeavingLazy();
	void setWeavingLazy(Boolean newWeavingLazy);
		static final String WEAVING_LAZY_PROPERTY = "weavingLazy"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_LAZY = "eclipselink.weaving.lazy"; //$NON-NLS-1$
		static final Boolean DEFAULT_WEAVING_LAZY = Boolean.TRUE;
	
	Boolean getDefaultWeavingChangeTracking();
	Boolean getWeavingChangeTracking();
	void setWeavingChangeTracking(Boolean newWeavingChangeTracking);
		static final String WEAVING_CHANGE_TRACKING_PROPERTY = "weavingChangeTracking"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_CHANGE_TRACKING = "eclipselink.weaving.changetracking"; //$NON-NLS-1$
		static final Boolean DEFAULT_WEAVING_CHANGE_TRACKING = Boolean.TRUE;

	Boolean getDefaultWeavingFetchGroups();
	Boolean getWeavingFetchGroups();
	void setWeavingFetchGroups(Boolean newWeavingFetchGroups);
		static final String WEAVING_FETCH_GROUPS_PROPERTY = "weavingFetchGroups"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_FETCH_GROUPS = "eclipselink.weaving.fetchgroups"; //$NON-NLS-1$
		static final Boolean DEFAULT_WEAVING_FETCH_GROUPS = Boolean.TRUE;

	Boolean getDefaultWeavingInternal();
	Boolean getWeavingInternal();
	void setWeavingInternal(Boolean newWeavingInternal);
		static final String WEAVING_INTERNAL_PROPERTY = "weavingInternal"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_INTERNAL = "eclipselink.weaving.internal"; //$NON-NLS-1$
		static final Boolean DEFAULT_WEAVING_INTERNAL = Boolean.TRUE;

	Boolean getDefaultWeavingEager();
	Boolean getWeavingEager();
	void setWeavingEager(Boolean newWeavingEager);
		static final String WEAVING_EAGER_PROPERTY = "weavingEager"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_EAGER = "eclipselink.weaving.eager"; //$NON-NLS-1$
		static final Boolean DEFAULT_WEAVING_EAGER = Boolean.FALSE;

	String getDefaultDescriptorCustomizer();
	String getDescriptorCustomizerOf(String entityName);
	void setDescriptorCustomizerOf(String entityName, String newDescriptorCustomizer);
		static final String DESCRIPTOR_CUSTOMIZER_PROPERTY = "descriptorCustomizer"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_DESCRIPTOR_CUSTOMIZER = "eclipselink.descriptor.customizer."; //$NON-NLS-1$
		static final String DEFAULT_DESCRIPTOR_CUSTOMIZER = null;	// no default

	ListIterator<String> sessionCustomizers();
	int sessionCustomizersSize();
	boolean sessionCustomizerExists(String sessionCustomizerClassName);
	String addSessionCustomizer(String newSessionCustomizerClassName);
	void removeSessionCustomizer(String sessionCustomizerClassName);
		static final String SESSION_CUSTOMIZER_LIST = "sessionCustomizers"; //$NON-NLS-1$
		static final String SESSION_CUSTOMIZER_PROPERTY = "sessionCustomizer"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_CUSTOMIZER = "eclipselink.session.customizer"; //$NON-NLS-1$

	ListIterator<Entity> entities();
	Iterator<String> entityNames();
	int entitiesSize();
	boolean entityExists(String entity);
	Entity addEntity(String entity);
	void removeEntity(String entity);
		static final String ENTITIES_LIST = "entities"; //$NON-NLS-1$
		
	String getDefaultProfiler();
	String getProfiler();
	void setProfiler(String newProfiler);
	void setProfiler(Profiler newProfiler);
		static final String PROFILER_PROPERTY = "profiler"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_PROFILER = "eclipselink.profiler"; //$NON-NLS-1$
		static final String DEFAULT_PROFILER = 
			AbstractPersistenceUnitProperties.getPropertyStringValueOf(Profiler.no_profiler);
		String ECLIPSELINK_SESSION_PROFILER_CLASS_NAME = "org.eclipse.persistence.sessions.SessionProfiler"; //$NON-NLS-1$
		
	Boolean getDefaultValidationOnly();
	Boolean getValidationOnly();
	void setValidationOnly(Boolean newValidationOnly);
		static final String VALIDATION_ONLY_PROPERTY = "validationOnly"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_VALIDATION_ONLY = "eclipselink.validation-only"; //$NON-NLS-1$
		static final Boolean DEFAULT_VALIDATION_ONLY = Boolean.TRUE;

	String getDefaultExceptionHandler();
	String getExceptionHandler();
	void setExceptionHandler(String newExceptionHandler);
		static final String EXCEPTION_HANDLER_PROPERTY = "exceptionHandler"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_EXCEPTION_HANDLER = "eclipselink.exception-handler"; //$NON-NLS-1$
		static final String DEFAULT_EXCEPTION_HANDLER = null;	// no default

		String ECLIPSELINK_EXCEPTION_HANDLER_CLASS_NAME = "org.eclipse.persistence.exceptions.ExceptionHandler"; //$NON-NLS-1$

	Boolean getDefaultValidateSchema();
	Boolean getValidateSchema();
	void setValidateSchema(Boolean newValidateSchema);
		static final String VALIDATE_SCHEMA_PROPERTY = "validateSchema"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_VALIDATE_SCHEMA = "eclipselink.orm.validate.schema"; //$NON-NLS-1$
		static final Boolean DEFAULT_VALIDATE_SCHEMA = Boolean.FALSE;
}
