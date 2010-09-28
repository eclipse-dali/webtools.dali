/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.libprov;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderFramework;
import org.eclipse.jst.common.project.facet.core.libprov.osgi.OsgiBundlesContainer;
import org.eclipse.jst.common.project.facet.core.libprov.osgi.OsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.jst.j2ee.classpathdep.ClasspathDependencyUtil;
import org.eclipse.jst.j2ee.classpathdep.IClasspathDependencyConstants;
import org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectBase;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;


public class TempWtpOsgiBundlesLibraryProviderInstallOperationConfig
		extends OsgiBundlesLibraryProviderInstallOperationConfig {
	
    private static final IProjectFacet WEB_FACET 
        = ProjectFacetsManager.getProjectFacet( IJ2EEFacetConstants.DYNAMIC_WEB );
    
    private static final String CLASS_NAME 
        = TempWtpOsgiBundlesLibraryProviderInstallOperationConfig.class.getName();
    
    public static final String PROP_INCLUDE_WITH_APPLICATION_ENABLED 
        = CLASS_NAME + ".INCLUDE_WITH_APPLICATION_ENABLED"; //$NON-NLS-1$

    private boolean includeWithApplicationEnabled;
    
    public static final String PROP_INCLUDE_WITH_APPLICATION_SETTING_ENABLED 
        = CLASS_NAME + ".INCLUDE_WITH_APPLICATION_SETTING_ENABLED"; //$NON-NLS-1$

    private boolean includeWithApplicationSettingEnabled;
    
    public boolean isIncludeWithApplicationEnabled()
    {
        return this.includeWithApplicationEnabled;
    }
    
    public void setIncludeWithApplicationEnabled( final boolean value )
    {
        final boolean oldValue = this.includeWithApplicationEnabled;
        this.includeWithApplicationEnabled = value;
        notifyListeners( PROP_INCLUDE_WITH_APPLICATION_ENABLED, oldValue, this.includeWithApplicationEnabled );
    }

    public boolean isIncludeWithApplicationSettingEnabled()
    {
        return this.includeWithApplicationSettingEnabled;
    }
    
    public void setIncludeWithApplicationSettingEnabled( final boolean value )
    {
        final boolean oldValue = this.includeWithApplicationSettingEnabled;
        this.includeWithApplicationSettingEnabled = value;
        notifyListeners( PROP_INCLUDE_WITH_APPLICATION_SETTING_ENABLED, oldValue, this.includeWithApplicationSettingEnabled );
    }

    @Override
    public synchronized void init( final IFacetedProjectBase fproj,
                                   final IProjectFacetVersion fv,
                                   final ILibraryProvider provider )
    {
        super.init( fproj, fv, provider );
        
        this.includeWithApplicationEnabled = isModuleFaceted( fproj );
        
        final IProject project = fproj.getProject();
        
        if( project != null )
        {
            final IProjectFacet f = fv.getProjectFacet();
            
            final ILibraryProvider currentProvider 
                = LibraryProviderFramework.getCurrentProvider( project, f );
            
            if( currentProvider == provider )
            {
            	this.includeWithApplicationEnabled = getIncludeWithApplicationSetting( project, f);
            }
        }
        
        this.includeWithApplicationSettingEnabled 
        	= ( this.includeWithApplicationEnabled ) ? true : isModuleFaceted( fproj );
		
		IFacetedProjectListener listener = new IFacetedProjectListener() 
		{
			public void handleEvent( final IFacetedProjectEvent event ) 
			{
				final boolean moduleFaceted = isModuleFaceted( event.getWorkingCopy() );
				setIncludeWithApplicationEnabled( moduleFaceted );
				setIncludeWithApplicationSettingEnabled( moduleFaceted );
			}
		};
		
		fproj.addListener( listener, IFacetedProjectEvent.Type.PROJECT_FACETS_CHANGED );
    }
    
    protected boolean isModuleFaceted( final IFacetedProjectBase fproj ) {
        for( IProjectFacetVersion facetVersion : fproj.getProjectFacets() ) 
        {
            if( ProjectFacetsManager.getGroup( "modules" ).getMembers().contains( facetVersion ) ) //$NON-NLS-1$
            {
                return true;
            }
        }
        return false;
    }
    
    private static boolean getIncludeWithApplicationSetting( final IProject proj, final IProjectFacet f )
    {
        final IPath path = OsgiBundlesContainer.CONTAINER_PATH.append( f.getId() );
        final IJavaProject jproj = JavaCore.create( proj );
                
        try
        {
            for( IClasspathEntry cpe : jproj.getRawClasspath() )
            {
                if( path.equals( cpe.getPath() ) )
                {
                    for( IClasspathAttribute attr : cpe.getExtraAttributes() )
                    {
                        if( attr.getName().equals( IClasspathDependencyConstants.CLASSPATH_COMPONENT_DEPENDENCY ) )
                        {
                            return true;
                        }
                    }
                }
            }
        }
        catch( CoreException e )
        {
            throw new RuntimeException( e );
        }
        
        return false;
    }
    
    @Override
    public IClasspathAttribute[] getClasspathAttributes()
    {
        final IFacetedProjectBase fproj = getFacetedProject();
        final boolean isWebProject = fproj.hasProjectFacet( WEB_FACET );
        
        if( isIncludeWithApplicationEnabled() )
        {
            final IClasspathAttribute attr 
            	= JavaCore.newClasspathAttribute( IClasspathDependencyConstants.CLASSPATH_COMPONENT_DEPENDENCY,
                                                  ClasspathDependencyUtil.getDefaultRuntimePath( isWebProject ).toString() );
            return new IClasspathAttribute[] { attr };
        }
        else if ( isIncludeWithApplicationSettingEnabled() )
        {
            final IClasspathAttribute attr 
            	= JavaCore.newClasspathAttribute( IClasspathDependencyConstants.CLASSPATH_COMPONENT_NON_DEPENDENCY, "" ); //$NON-NLS-1$
            return new IClasspathAttribute[] { attr };
        }
        
        return new IClasspathAttribute[ 0 ];
    }
}
