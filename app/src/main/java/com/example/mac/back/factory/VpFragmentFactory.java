package com.example.mac.back.factory;

import com.example.mac.back.ui.fragment.Fragment_blank;
import com.example.mac.back.ui.fragment.Fragment_home;
import com.example.mac.back.ui.fragment.Fragment_more;
import com.example.mac.back.ui.fragment.Fragment_my;
import com.example.mac.back.ui.fragment.Fragment_product;

/**
 * Created by mac on 2018/3/17.
 */

public class VpFragmentFactory {

        public static VpFragment producePage(String pagename) {

            switch (pagename) {
                case "home":
                    return new Fragment_home();
                case "more":
                    return new Fragment_more();
                case "my":
                    return new Fragment_my();
                case "product":
                    return new Fragment_product();
                default:
                    return new Fragment_blank();
            }



        }

}
