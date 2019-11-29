package ua.com.crosp.solutions.hearthealthmonitor.base.routing;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.FragmentController;


public abstract class BaseMultipleFragmentActivity extends BaseActivity
        implements FragmentController.Multiple {
    // Variables
    protected Class mCurrentFragmentClass;


    @Override
    public void popFragmentFromBackStack(@IdRes int container) {
        mFragmentManager.popBackStack();
    }

    /**
     * On create get fragment provided by child activities
     * adds it to the view hierarchy
     *
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * Replacing fragment by class
     *
     * @param fragmentClass  fragment class object
     * @param arguments      bundle for passing arguments
     * @param addToBackStack add fragment to backstack
     * @return success
     */
    @Override
    public boolean replaceFragment(Class fragmentClass, @IdRes int container, Bundle arguments, boolean addToBackStack, boolean clearBackStack) {
        if (clearBackStack) {
            mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (!isInBackStack(fragmentClass, container)) {
            mCurrentFragmentClass = fragmentClass;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            Fragment fragment = null;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            assert fragment != null;
            fragment.setArguments(arguments);
            fragmentTransaction.replace(container, fragment);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(getFragmentClassKey(fragmentClass));
            }
            fragmentTransaction.commit();
            return true;
        }

        return false;
    }

    /**
     * Replace fragment without bundle
     *
     * @param fragmentClass  fragment class to be replaced with
     * @param addToBackStack add to back stack
     * @return success
     */
    @Override
    public boolean replaceFragment(Class fragmentClass, int container, boolean addToBackStack) {
        return this.replaceFragment(fragmentClass, container, Bundle.EMPTY, addToBackStack, false);
    }

    /**
     * Replace fragment without bundle
     *
     * @param fragmentClass  fragment class to be replaced with
     * @param addToBackStack add to back stack
     * @return success
     */
    @Override
    public boolean replaceFragment(Class fragmentClass, int container, boolean addToBackStack, boolean clearBackStack) {
        return this.replaceFragment(fragmentClass, container, Bundle.EMPTY, addToBackStack, clearBackStack);
    }

    /**
     * If fragment is already exists in back stack if so - don't replace it
     * and add to the back stack
     *
     * @param fragmentClass fragment class in order to compare with back stack entries
     * @return if fragment is already in back stack
     */
    public boolean isInBackStack(Class fragmentClass, int container) {
        Fragment currentFragment = mFragmentManager.findFragmentById(container);
        if (currentFragment != null && getFragmentClassKey(currentFragment.getClass()).equals(getFragmentClassKey(fragmentClass))) {
            return true;
        }
        int count = mFragmentManager.getBackStackEntryCount();
        if (count <= 0) {
            return false;
        }
        for (int i = 0; i < count; i++) {
            if (mFragmentManager.getBackStackEntryAt(i).getName().equals(getFragmentClassKey(fragmentClass))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get unique data which describes fragment
     *
     * @param fragmentClass fragment class
     * @return unique string for fragment class
     */
    protected String getFragmentClassKey(Class fragmentClass) {
        return fragmentClass.getCanonicalName();
    }


}
