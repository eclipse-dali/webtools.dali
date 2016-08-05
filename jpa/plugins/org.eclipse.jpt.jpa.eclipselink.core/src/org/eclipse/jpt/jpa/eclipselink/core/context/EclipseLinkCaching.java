/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.int_.IntTransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.IntTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;

/**
 * EclipseLink caching
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 2.1
 */
// TODO bjv EclipseLinkCachingPolicy
public interface EclipseLinkCaching
	extends JpaContextModel
{
	// ********** type **********

	/**
	 * This is the combination of defaultType and specifiedType.
	 * If getSpecifiedType() returns null, then return getDefaultType()
	 */
	EclipseLinkCacheType getType();
		String TYPE_PROPERTY = "type"; //$NON-NLS-1$
	
	EclipseLinkCacheType getSpecifiedType();	
	void setSpecifiedType(EclipseLinkCacheType type);
		String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, EclipseLinkCacheType> SPECIFIED_TYPE_TRANSFORMER = new SpecifiedTypeTransformer();
		class SpecifiedTypeTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheType>
		{
			@Override
			public EclipseLinkCacheType transform(EclipseLinkCaching model) {
				return model.getSpecifiedType();
			}
		}
		BiClosure<EclipseLinkCaching, EclipseLinkCacheType> SET_SPECIFIED_TYPE_CLOSURE = new SetSpecifiedTypeClosure();
		class SetSpecifiedTypeClosure
			extends BiClosureAdapter<EclipseLinkCaching, EclipseLinkCacheType>
		{
			@Override
			public void execute(EclipseLinkCaching model, EclipseLinkCacheType type) {
				model.setSpecifiedType(type);
			}
		}

	EclipseLinkCacheType getDefaultType();		
		String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
		EclipseLinkCacheType DEFAULT_TYPE = EclipseLinkCacheType.SOFT_WEAK;
		Transformer<EclipseLinkCaching, EclipseLinkCacheType> DEFAULT_TYPE_TRANSFORMER = new DefaultTypeTransformer();
		class DefaultTypeTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheType>
		{
			@Override
			public EclipseLinkCacheType transform(EclipseLinkCaching model) {
				return model.getDefaultType();
			}
		}
		
	
	// ********** size **********
			
	/**
	 * This is the combination of defaultSize and specifiedSize.
	 * If getSpecifiedSize() returns null, then return getDefaultSize()
	 */
	int getSize();
		String SIZE_PROPERTY = "size"; //$NON-NLS-1$

	Integer getSpecifiedSize();
	void setSpecifiedSize(Integer size);
		String SPECIFIED_SIZE_PROPERTY = "specifiedSize"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, Integer> SPECIFIED_SIZE_TRANSFORMER = new SpecifiedSizeTransformer();
		class SpecifiedSizeTransformer
			extends TransformerAdapter<EclipseLinkCaching, Integer>
		{
			@Override
			public Integer transform(EclipseLinkCaching model) {
				return model.getSpecifiedSize();
			}
		}
		BiClosure<EclipseLinkCaching, Integer> SET_SPECIFIED_SIZE_CLOSURE = new SetSpecifiedSizeClosure();
		class SetSpecifiedSizeClosure
			extends BiClosureAdapter<EclipseLinkCaching, Integer>
		{
			@Override
			public void execute(EclipseLinkCaching model, Integer size) {
				model.setSpecifiedSize(size);
			}
		}
	
	int getDefaultSize();
		String DEFAULT_SIZE_PROPERTY = "defaultSize"; //$NON-NLS-1$
		int DEFAULT_SIZE = 100;
		IntTransformer<EclipseLinkCaching> DEFAULT_SIZE_TRANSFORMER = new DefaultSizeTransformer();
		class DefaultSizeTransformer
			extends IntTransformerAdapter<EclipseLinkCaching>
		{
			@Override
			public int transform(EclipseLinkCaching model) {
				return model.getDefaultSize();
			}
		}


	// ********** shared **********
	
	/**
	 * This is the combination of defaultShared and specifiedShared.
	 * If getSpecifiedShared() returns null, then return isDefaultShared()
	 */
	boolean isShared();
		String SHARED_PROPERTY = "shared"; //$NON-NLS-1$
		Predicate<EclipseLinkCaching> SHARED_PREDICATE = new SharedPredicate();
		class SharedPredicate
			extends PredicateAdapter<EclipseLinkCaching>
		{
			@Override
			public boolean evaluate(EclipseLinkCaching model) {
				return model.isShared();
			}
		}
	
	Boolean getSpecifiedShared();

	/**
	 * Specifying <em>shared</em> <code>false</code> will return the following
	 * caching settings to their defaults:<ul>
	 * <li>type
	 * <li>size
	 * <li>always refresh
	 * <li>refresh only if newer
	 * <li>disable hits
	 * <li>coordination type
	 * </ul>
	 * Additionally, the following settings will be cleared:<ul>
	 * <li>expiry
	 * <li>expiry time of day
	 * </ul>
	 * These settings do not apply to an unchared cache.
	 */
	void setSpecifiedShared(Boolean shared);
		String SPECIFIED_SHARED_PROPERTY = "specifiedShared"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, Boolean> SPECIFIED_SHARED_TRANSFORMER = new SpecifiedSharedTransformer();
		class SpecifiedSharedTransformer
			extends TransformerAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkCaching model) {
				return model.getSpecifiedShared();
			}
		}
		BiClosure<EclipseLinkCaching, Boolean> SET_SPECIFIED_SHARED_CLOSURE = new SetSpecifiedSharedClosure();
		class SetSpecifiedSharedClosure
			extends BiClosureAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public void execute(EclipseLinkCaching model, Boolean value) {
				model.setSpecifiedShared(value);
			}
		}

	boolean getDefaultShared();
		String DEFAULT_SHARED_PROPERTY = "defaultShared"; //$NON-NLS-1$
		boolean DEFAULT_SHARED = true;
		Predicate<EclipseLinkCaching> DEFAULT_SHARED_PREDICATE = new DefaultSharedPredicate();
		class DefaultSharedPredicate
			extends PredicateAdapter<EclipseLinkCaching>
		{
			@Override
			public boolean evaluate(EclipseLinkCaching caching) {
				return caching.getDefaultShared();
			}
		}
	
	
	// ********** always refresh **********
		
	/**
	 * This is the combination of defaultAlwaysRefresh and specifiedAlwaysRefresh.
	 * If getSpecifiedAlwaysRefresh() returns null, then return isDefaultAlwaysRefresh()
	 */
	boolean isAlwaysRefresh();
		String ALWAYS_REFRESH_PROPERTY = "alwaysRefresh"; //$NON-NLS-1$
	
	Boolean getSpecifiedAlwaysRefresh();
	void setSpecifiedAlwaysRefresh(Boolean alwaysRefresh);
		String SPECIFIED_ALWAYS_REFRESH_PROPERTY = "specifiedAlwaysRefresh"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, Boolean> SPECIFIED_ALWAYS_REFRESH_TRANSFORMER = new SpecifiedAlwaysRefreshTransformer();
		class SpecifiedAlwaysRefreshTransformer
			extends TransformerAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkCaching model) {
				return model.getSpecifiedAlwaysRefresh();
			}
		}
		BiClosure<EclipseLinkCaching, Boolean> SET_SPECIFIED_ALWAYS_REFRESH_CLOSURE = new SetSpecifiedAlwaysRefreshClosure();
		class SetSpecifiedAlwaysRefreshClosure
			extends BiClosureAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public void execute(EclipseLinkCaching model, Boolean value) {
				model.setSpecifiedAlwaysRefresh(value);
			}
		}

	boolean getDefaultAlwaysRefresh();
		String DEFAULT_ALWAYS_REFRESH_PROPERTY = "defaultAlwaysRefresh"; //$NON-NLS-1$
		boolean DEFAULT_ALWAYS_REFRESH = false;
		Predicate<EclipseLinkCaching> DEFAULT_ALWAYS_REFRESH_TRANSFORMER = new DefaultAlwaysRefreshPredicate();
		class DefaultAlwaysRefreshPredicate
			extends PredicateAdapter<EclipseLinkCaching>
		{
			@Override
			public boolean evaluate(EclipseLinkCaching variable) {
				return variable.getDefaultAlwaysRefresh();
			}
		}
	

	// ********** refresh only if newer **********
		
	/**
	 * This is the combination of defaultRefreshOnlyIfNewer and specifiedRefreshOnlyIfNewer.
	 * If getSpecifiedRefreshOnlyIfNewer() returns null, then return isDefaultRefreshOnlyIfNewer()
	 */
	boolean isRefreshOnlyIfNewer();
		String REFRESH_ONLY_IF_NEWER_PROPERTY = "refreshOnlyIfNewer"; //$NON-NLS-1$
	
	Boolean getSpecifiedRefreshOnlyIfNewer();
	void setSpecifiedRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer);
		String SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY = "specifiedRefreshOnlyIfNewer"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, Boolean> SPECIFIED_REFRESH_ONLY_IF_NEWER_TRANSFORMER = new SpecifiedRefreshOnlyIfNewerTransformer();
		class SpecifiedRefreshOnlyIfNewerTransformer
			extends TransformerAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkCaching model) {
				return model.getSpecifiedRefreshOnlyIfNewer();
			}
		}
		BiClosure<EclipseLinkCaching, Boolean> SET_SPECIFIED_REFRESH_ONLY_IF_NEWER_CLOSURE = new SetSpecifiedRefreshOnlyIfNewerClosure();
		class SetSpecifiedRefreshOnlyIfNewerClosure
			extends BiClosureAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public void execute(EclipseLinkCaching model, Boolean value) {
				model.setSpecifiedRefreshOnlyIfNewer(value);
			}
		}

	boolean getDefaultRefreshOnlyIfNewer();
		String DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY = "defaultRefreshOnlyIfNewer"; //$NON-NLS-1$
		boolean DEFAULT_REFRESH_ONLY_IF_NEWER = false;
		Predicate<EclipseLinkCaching> DEFAULT_REFRESH_ONLY_IF_NEWER_TRANSFORMER = new DefaultRefreshOnlyIfNewerPredicate();
		class DefaultRefreshOnlyIfNewerPredicate
			extends PredicateAdapter<EclipseLinkCaching>
		{
			@Override
			public boolean evaluate(EclipseLinkCaching variable) {
				return variable.getDefaultRefreshOnlyIfNewer();
			}
		}
	
		
	// ********** disable hits **********
		
	/**
	 * This is the combination of defaultDisableHits and specifiedDisableHits.
	 * If getSpecifiedDisableHits() returns null, then return getDefaultDisableHits()
	 */
	boolean isDisableHits();
		String DISABLE_HITS_PROPERTY = "disableHits"; //$NON-NLS-1$
	
	Boolean getSpecifiedDisableHits();
	void setSpecifiedDisableHits(Boolean disableHits);
		String SPECIFIED_DISABLE_HITS_PROPERTY = "specifiedDisableHits"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, Boolean> SPECIFIED_DISABLE_HITS_TRANSFORMER = new SpecifiedDisableHitsTransformer();
		class SpecifiedDisableHitsTransformer
			extends TransformerAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkCaching model) {
				return model.getSpecifiedDisableHits();
			}
		}
		BiClosure<EclipseLinkCaching, Boolean> SET_SPECIFIED_DISABLE_HITS_CLOSURE = new SetSpecifiedDisableHitsClosure();
		class SetSpecifiedDisableHitsClosure
			extends BiClosureAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public void execute(EclipseLinkCaching model, Boolean value) {
				model.setSpecifiedDisableHits(value);
			}
		}

	boolean getDefaultDisableHits();
		String DEFAULT_DISABLE_HITS_PROPERTY = "defaultDisableHits"; //$NON-NLS-1$
		boolean DEFAULT_DISABLE_HITS = false;
		Predicate<EclipseLinkCaching> DEFAULT_DISABLE_HITS_TRANSFORMER = new DefaultDisableHitsPredicate();
		class DefaultDisableHitsPredicate
			extends PredicateAdapter<EclipseLinkCaching>
		{
			@Override
			public boolean evaluate(EclipseLinkCaching variable) {
				return variable.getDefaultDisableHits();
			}
		}
	

		
	// ********** coordination type **********
	
	/**
	 * This is the combination of defaultCoordinationType and specifiedCoordinationType.
	 * If getSpecifiedCoordinationType() returns null, then return getDefaultCoordinationType()
	 */
	EclipseLinkCacheCoordinationType getCoordinationType();
		String COORDINATION_TYPE_PROPERTY = "coordinationType"; //$NON-NLS-1$
	
	EclipseLinkCacheCoordinationType getSpecifiedCoordinationType();	
	void setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType coordinationType);
		String SPECIFIED_COORDINATION_TYPE_PROPERTY = "specifiedCoordinationType"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, EclipseLinkCacheCoordinationType> SPECIFIED_COORDINATION_TYPE_TRANSFORMER = new SpecifiedCoordinationTypeTransformer();
		class SpecifiedCoordinationTypeTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheCoordinationType>
		{
			@Override
			public EclipseLinkCacheCoordinationType transform(EclipseLinkCaching model) {
				return model.getSpecifiedCoordinationType();
			}
		}
		BiClosure<EclipseLinkCaching, EclipseLinkCacheCoordinationType> SET_SPECIFIED_COORDINATION_TYPE_CLOSURE = new SetSpecifiedCoordinationTypeClosure();
		class SetSpecifiedCoordinationTypeClosure
			extends BiClosureAdapter<EclipseLinkCaching, EclipseLinkCacheCoordinationType>
		{
			@Override
			public void execute(EclipseLinkCaching model, EclipseLinkCacheCoordinationType type) {
				model.setSpecifiedCoordinationType(type);
			}
		}

	EclipseLinkCacheCoordinationType getDefaultCoordinationType();		
		String DEFAULT_COORDINATION_TYPE_PROPERTY = "defaultCoordinationType"; //$NON-NLS-1$
		EclipseLinkCacheCoordinationType DEFAULT_COORDINATION_TYPE = EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES;
		Transformer<EclipseLinkCaching, EclipseLinkCacheCoordinationType> DEFAULT_COORDINATION_TYPE_TRANSFORMER = new DefaultCoordinationTypeTransformer();
		class DefaultCoordinationTypeTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheCoordinationType>
		{
			@Override
			public EclipseLinkCacheCoordinationType transform(EclipseLinkCaching model) {
				return model.getDefaultCoordinationType();
			}
		}

		
	// ********** expiry **********

	/**
	 * corresponds to the Cache expiry element.  If this returns
	 * a non-null value then getExpiryTimeOfDay will return null.
	 * It is not valid to specify both
	 */
	Integer getExpiry();
	
	/**
	 * Setting this to a non-null value will set timeOfDayExpiry to null
	 */
	void setExpiry(Integer expiry);
		String EXPIRY_PROPERTY = "expiry"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, Integer> EXPIRY_TRANSFORMER = new ExpiryTransformer();
		class ExpiryTransformer
			extends TransformerAdapter<EclipseLinkCaching, Integer>
		{
			@Override
			public Integer transform(EclipseLinkCaching model) {
				return model.getExpiry();
			}
		}
		BiClosure<EclipseLinkCaching, Integer> SET_EXPIRY_CLOSURE = new SetExpiryClosure();
		class SetExpiryClosure
			extends BiClosureAdapter<EclipseLinkCaching, Integer>
		{
			@Override
			public void execute(EclipseLinkCaching model, Integer expiry) {
				model.setExpiry(expiry);
			}
		}

		
	// ********** expiry time of day **********

	/**
	 * corresponds to the Cache expiryTimeOfDay annotation or xml element.  
	 * If this returns a non-null value then getExpiry will return null.
	 * It is not valid to specify both.
	 */
	EclipseLinkTimeOfDay getExpiryTimeOfDay();
		String EXPIRY_TIME_OF_DAY_PROPERTY = "expiryTimeOfDay"; //$NON-NLS-1$
	
	/**
	 * Add Cache expiryTimeOfDay annotation or xml element, this will set 
	 * Expiry to null as it is not valid to set both expiry and timeOfDayExpiry
	 */
	EclipseLinkTimeOfDay addExpiryTimeOfDay();
	
	/**
	 * Removes the Cache expiryTimeOfDay annotation/xml element
	 */
	void removeExpiryTimeOfDay();
	

	// ********** existence type **********
	// TODO bjv rename existenceCheckingPolicy
	/**
	 * This is the combination of defaultExistenceType and specifiedExistenceType.
	 * If getSpecifiedExistenceType() returns null, then return getDefaultExistenceType()
	 */
	EclipseLinkExistenceType getExistenceType();
		String EXISTENCE_TYPE_PROPERTY = "existenceType"; //$NON-NLS-1$
	
	EclipseLinkExistenceType getSpecifiedExistenceType();	
	void setSpecifiedExistenceType(EclipseLinkExistenceType type);
		String SPECIFIED_EXISTENCE_TYPE_PROPERTY = "specifiedExistenceType"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, EclipseLinkExistenceType> SPECIFIED_EXISTENCE_TYPE_TRANSFORMER = new SpecifiedExistenceTypeTransformer();
		class SpecifiedExistenceTypeTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkExistenceType>
		{
			@Override
			public EclipseLinkExistenceType transform(EclipseLinkCaching model) {
				return model.getSpecifiedExistenceType();
			}
		}
		BiClosure<EclipseLinkCaching, EclipseLinkExistenceType> SET_SPECIFIED_EXISTENCE_TYPE_CLOSURE = new SetSpecifiedExistenceTypeClosure();
		class SetSpecifiedExistenceTypeClosure
			extends BiClosureAdapter<EclipseLinkCaching, EclipseLinkExistenceType>
		{
			@Override
			public void execute(EclipseLinkCaching model, EclipseLinkExistenceType type) {
				model.setSpecifiedExistenceType(type);
			}
		}

	EclipseLinkExistenceType getDefaultExistenceType();		
		String DEFAULT_EXISTENCE_TYPE_PROPERTY = "defaultExistenceType"; //$NON-NLS-1$
		EclipseLinkExistenceType DEFAULT_EXISTENCE_TYPE = EclipseLinkExistenceType.CHECK_DATABASE;
		Transformer<EclipseLinkCaching, EclipseLinkExistenceType> DEFAULT_EXISTENCE_TYPE_TRANSFORMER = new DefaultExistenceTypeTransformer();
		class DefaultExistenceTypeTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkExistenceType>
		{
			@Override
			public EclipseLinkExistenceType transform(EclipseLinkCaching model) {
				return model.getDefaultExistenceType();
			}
		}


	// ********** isolation added in EclipseLink 2.2 **********

	/**
	 * This is the combination of defaultIsolation and specifiedIsolation.
	 * If getSpecifiedIsolation() returns null, then return getDefaultIsolation()
	 */
	EclipseLinkCacheIsolationType2_2 getIsolation();
		String ISOLATION_PROPERTY = "isolation"; //$NON-NLS-1$

	EclipseLinkCacheIsolationType2_2 getSpecifiedIsolation();	
	void setSpecifiedIsolation(EclipseLinkCacheIsolationType2_2 isolation);
		String SPECIFIED_ISOLATION_PROPERTY = "specifiedIsolation"; //$NON-NLS-1$
		Transformer<EclipseLinkCaching, EclipseLinkCacheIsolationType2_2> SPECIFIED_ISOLATION_TRANSFORMER = new SpecifiedIsolationTransformer();
		class SpecifiedIsolationTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheIsolationType2_2>
		{
			@Override
			public EclipseLinkCacheIsolationType2_2 transform(EclipseLinkCaching model) {
				return model.getSpecifiedIsolation();
			}
		}
		BiClosure<EclipseLinkCaching, EclipseLinkCacheIsolationType2_2> SET_SPECIFIED_ISOLATION_CLOSURE = new SetSpecifiedIsolationClosure();
		class SetSpecifiedIsolationClosure
			extends BiClosureAdapter<EclipseLinkCaching, EclipseLinkCacheIsolationType2_2>
		{
			@Override
			public void execute(EclipseLinkCaching model, EclipseLinkCacheIsolationType2_2 type) {
				model.setSpecifiedIsolation(type);
			}
		}

	EclipseLinkCacheIsolationType2_2 getDefaultIsolation();		
		String DEFAULT_ISOLATION_PROPERTY = "defaultIsolation"; //$NON-NLS-1$
		EclipseLinkCacheIsolationType2_2 DEFAULT_ISOLATION = EclipseLinkCacheIsolationType2_2.SHARED;
		Transformer<EclipseLinkCaching, EclipseLinkCacheIsolationType2_2> DEFAULT_ISOLATION_TRANSFORMER = new DefaultIsolationTransformer();
		class DefaultIsolationTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheIsolationType2_2>
		{
			@Override
			public EclipseLinkCacheIsolationType2_2 transform(EclipseLinkCaching model) {
				return model.getDefaultIsolation();
			}
		}
}
