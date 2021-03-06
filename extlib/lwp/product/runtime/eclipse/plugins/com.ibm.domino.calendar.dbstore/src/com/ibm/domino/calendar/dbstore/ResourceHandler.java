 /*
 * � Copyright IBM Corp. 2011
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
/** Generated by Workplace Designer localization tool **/
package com.ibm.domino.calendar.dbstore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ibm.domino.commons.RequestContext;

/** Resource handler class **/
public class ResourceHandler {

    private static Map<Locale, ResourceBundle> _resourceBundleMap = new HashMap<Locale, ResourceBundle>();
    private static ResourceBundle _loggingResourceBundle;
    private static Map<Locale, ResourceBundle> _specialAudienceBundleMap = new HashMap<Locale, ResourceBundle>();

    private static ResourceBundle getResourceBundle(String bundle, Locale locale) {
        try {
            String bundlePackage = buildResourcePath(bundle);
            return ResourceBundle.getBundle( bundlePackage, locale );
        }
        catch (MissingResourceException e) {
            // does nothing - this method will return null and
            // getString(String) will return the key
            // it was called with
        }
        return null;
    }
    private static String buildResourcePath(String name) {
        String clName = ResourceHandler.class.getName();
        return clName.substring( 0, clName.lastIndexOf('.') + 1 ) + name; 
    }

    /**
     * @param key
     * @return
     */
    public static String getString(String key) {
        Locale locale = getUserLocale();
        ResourceBundle bundle = _resourceBundleMap.get(locale);
        if ( bundle == null ) {
            bundle = getResourceBundle("messages", locale); //$NON-NLS-1$
            _resourceBundleMap.put(locale, bundle);
        }

        return getResourceBundleString(bundle, key);
    }

    /**
     * @param key
     * @return
     */
    public static String getLoggingString(String key) {
        if (_loggingResourceBundle == null) {
            _loggingResourceBundle = getResourceBundle("logging", Locale.getDefault());//$NON-NLS-1$
        }
        return getResourceBundleString(_loggingResourceBundle, key);
    }

    /**
     * @param key
     * @return
     */
    public static String getSpecialAudienceString(String key){
        Locale locale = getUserLocale();
        ResourceBundle bundle = _specialAudienceBundleMap.get(locale);
        if ( bundle == null ) {
            bundle = getResourceBundle("specialAudience", locale); //$NON-NLS-1$
            _specialAudienceBundleMap.put(locale, bundle);
        }

        return getResourceBundleString(bundle, key);
    }

    private static String getResourceBundleString(ResourceBundle bundle, String key){
       if (bundle != null) {
            try {
                return bundle.getString(key);
            }
            catch (MissingResourceException e) {
                return "!" + key + "!";//$NON-NLS-2$//$NON-NLS-1$
            }
        }
        else {
            return "!" + key + "!";//$NON-NLS-2$//$NON-NLS-1$
        }
    }

    private static Locale getUserLocale(){
        // Try getting the locale from the request context (e.g. REST request)
        RequestContext rctx = RequestContext.getCurrentInstance();
        Locale locale = rctx.getUserLocale();
        
        if ( locale == null ) {
            locale = Locale.getDefault();
        }
        
        return locale;
    }
}
