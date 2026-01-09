package io.github.experionplanet.mysticalplantslg.utils;

// CREDIT GITHUB: Michaelangel007/easing/blob/master/js/core/easing.js
// CHANGED SOME KEYWORDS IN CODE EASILY WITH SEARCH REPLACE
public class EasingsList {
    public static double None           (double p) { return 1;               }; // p^0 Placeholder for no active animation
    public static double Linear         (double p) { return p;               }; // p^1 Note: In = Out = InOut
    public static double InQuadratic    (double p) { return p*p;             }; // p^2 = Math.pow(p,2)
    public static double InCubic        (double p) { return p*p*p;           }; // p^3 = Math.pow(p,3)
    public static double InQuartic      (double p) { return p*p*p*p;         }; // p^4 = Math.pow(p,4)
    public static double InQuintic      (double p) { return p*p*p*p*p;       }; // p^5 = Math.pow(p,5)
    public static double InSextic       (double p) { return p*p*p*p*p*p;     }; // p^6 = Math.pow(p,6)
    public static double InSeptic       (double p) { return p*p*p*p*p*p*p;   }; // p^7 = Math.pow(p,7)
    public static double InOctic        (double p) { return p*p*p*p*p*p*p*p; }; // p^8 = Math.pow(p,8)

    public static double OutQuadratic   (double p) { double m=p-1; return 1-m*m;             };
    public static double OutCubic       (double p) { double m=p-1; return 1+m*m*m;           };
    public static double OutQuartic     (double p) { double m=p-1; return 1-m*m*m*m;         };
    public static double OutQuintic     (double p) { double m=p-1; return 1+m*m*m*m*m;       };
    public static double OutSextic      (double p) { double m=p-1; return 1-m*m*m*m*m*m;     };
    public static double OutSeptic      (double p) { double m=p-1; return 1+m*m*m*m*m*m*m;   };
    public static double OutOctic       (double p) { double m=p-1; return 1-m*m*m*m*m*m*m*m; };

    public static double InOutQuadratic (double p) { double m=p-1;double t=p*2; if (t < 1) return p*t;             return 1-m*m            *  2; };
    public static double InOutCubic     (double p) { double m=p-1;double t=p*2; if (t < 1) return p*t*t;           return 1+m*m*m          *  4; };
    public static double InOutQuartic   (double p) { double m=p-1;double t=p*2; if (t < 1) return p*t*t*t;         return 1-m*m*m*m        *  8; };
    public static double InOutQuintic   (double p) { double m=p-1;double t=p*2; if (t < 1) return p*t*t*t*t;       return 1+m*m*m*m*m      * 16; };
    public static double InOutSextic    (double p) { double m=p-1;double t=p*2; if (t < 1) return p*t*t*t*t*t;     return 1-m*m*m*m*m*m    * 32; };
    public static double InOutSeptic    (double p) { double m=p-1;double t=p*2; if (t < 1) return p*t*t*t*t*t*t;   return 1+m*m*m*m*m*m*m  * 64; };
    public static double InOutOctic     (double p) { double m=p-1;double t=p*2; if (t < 1) return p*t*t*t*t*t*t*t; return 1-m*m*m*m*m*m*m*m*128; };

    public static double InBack         (double p) { double              k = 1.70158        ;              return p*p*(p*(k+1) - k);                                        };
    public static double InOutBack      (double p) { double m=p-1;double t=p*2, k = 1.70158 * 1.525; if (p < 0.5) return p*t*(t*(k+1) - k); else return 1 + 2*m*m*(2*m*(k+1) + k); }; // NOTE: Can go negative! i.e. p = 0.008
    public static double OutBack        (double p) { double m=p-1;double k = 1.70158        ;                                             return 1 +   m*m*(  m*(k+1) + k); };

    public static double InBounce       (double p) { return 1 - EasingsList.OutBounce( 1-p ); };
    public static double InOutBounce    (double p) {
        double t = p*2;
        if (t < 1) return 0.5 - 0.5 * EasingsList.OutBounce( 1 - t );
        return            0.5 + 0.5 * EasingsList.OutBounce( t - 1 );
    };
    public static double OutBounce      (double p) {
        double r  = 1  / 2.75; // reciprocal
        double k1 =         r; // 36.36%
        double k2 = 2     * r; // 72.72%
        double k3 = 1.5   * r; // 54.54%
        double k4 = 2.5   * r; // 90.90%
        double k5 = 2.25  * r; // 81.81%
        double k6 = 2.625 * r; // 95.45%
        double k0 = 7.5625;
        double t;

        /**/ if (p < k1) {             return k0 * p*p;            }
        else if (p < k2) { t = p - k3; return k0 * t*t + 0.75;     } // 48/64
        else if (p < k4) { t = p - k5; return k0 * t*t + 0.9375;   } // 60/64
        else             { t = p - k6; return k0 * t*t + 0.984375; } // 63/64
    };

    public static double InCircle       (double p) {                             return  1-Math.sqrt( 1 - p*p );                                                      };
    public static double InOutCircle    (double p) { double m=p-1; double t=p*2; if (t < 1) return (1-Math.sqrt( 1 - t*t ))*0.5; else return (Math.sqrt( 1 - 4*m*m ) + 1) * 0.5; };
    public static double OutCircle      (double p) { double m=p-1      ;                                                      return  Math.sqrt( 1 -   m*m );            };

    public static double InElastic      (double p) { double m = p-1; return  - Math.pow( 2,10*m  ) * Math.sin( ( m*40 - 3) * Math.PI/6  ); };
    public static double InOutElastic   (double p) {
        double s = 2*p-1;                 // remap: [0,0.5] -> [-1,0]
        double k = (80*s-9) * Math.PI/18; // and    [0.5,1] -> [0,+1]

        if (s < 0) return   -0.5*Math.pow(2, 10*s) * Math.sin( k );
        else       return 1 +0.5*Math.pow(2,-10*s) * Math.sin( k );
    };
    public static double OutElastic     (double p) {              return 1+(Math.pow( 2,10*-p ) * Math.sin( (-p*40 - 3) * Math.PI/6 )); };

    // NOTE: 'Exponent2' needs clamping for 0 and 1 respectively
    public static double InExponent2    (double p) {   if (p <= 0) return 0; return   Math.pow( 2,  10*(p-1) ); };
    public static double InOutExponent2 (double p) {
        if (p <= 0) return 0;
        if (p >= 1) return 1;
        if (p <0.5) return             Math.pow( 2,  10*(2*p-1)-1);
        else        return           1-Math.pow( 2, -10*(2*p-1)-1);
    };
    public static double OutExponent2   (double p)  {   if (p >= 1) return 1; return 1-Math.pow( 2, -10* p    ); };


    public static double InSine         (double p) { return      1 - Math.cos( p * Math.PI*0.5 );  };
    public static double InOutSine      (double p) { return 0.5*(1 - Math.cos( p * Math.PI     )); };
    public static double OutSine        (double p) { return          Math.sin( p * Math.PI*0.5 );  };
}