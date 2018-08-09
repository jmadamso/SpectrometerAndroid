package jmadamso.spectrometer;

/**
 * Created by Joseph on 5/26/2018.
 * Holds constants, message types, and commands
 */

public class defines {
    // BTService outgoing messages
    static final int MESSAGE_STATE_CHANGE = 1;
    static final int MESSAGE_READ = 2;
    static final int MESSAGE_WRITE = 3;
    static final int MESSAGE_DEVICE_NAME = 4;
    static final int MESSAGE_TOAST = 5;

    // Key names received from the BTService Handler
    static final String DEVICE_NAME = "device_name";
    static final String TOAST = "toast";

    // Spectrometer commands. Identical to the enum on the pi side
    static final char MOTOR_ON = 'a';
    static final char MOTOR_OFF = 'b';
    static final char LED_ON = 'c';
    static final char LED_OFF = 'd';
    static final char REQUEST_PRESSURE = 'e';   //toggle pressure stream
    static final char SNAPSHOT = 'f';    //request one spectrum reading
    static final char START_STREAM = 'g';
    static final char STOP_STREAM = 'h';          //stream continuous spectra
    static final char SETTINGS = 'i';           //apply or receive user settings
    static final char EXP_START = 'j';          //begin the experiment
    static final char EXP_STOP = 'k';           //stop the experiment
    static final char EXP_STATUS = 'l';         //return status (available/running) and settings
                                                    //of current experiment
    static final char EXP_LIST = 'm';           //return a list of completed experiments
    static final char EXP_LOOKUP = 'n' ;        //begin stream process of specific experiment
    static final char EXP_DELETE = 'o';         //remove a specific experiment



    static final int BT_STRING_SIZE = 1024;
    static final int NUM_WAVELENGTHS = 1024;

    static final double[] wavelengthArray = {
            337.26395,
            337.71746,
            338.17101,
            338.62460,
            339.07824,
            339.53192,
            339.98564,
            340.43940,
            340.89320,
            341.34705,
            341.80094,
            342.25486,
            342.70884,
            343.16285,
            343.61690,
            344.07100,
            344.52514,
            344.97932,
            345.43354,
            345.88781,
            346.34212,
            346.79646,
            347.25085,
            347.70529,
            348.15976,
            348.61428,
            349.06884,
            349.52344,
            349.97808,
            350.43276,
            350.88749,
            351.34225,
            351.79706,
            352.25192,
            352.70681,
            353.16174,
            353.61672,
            354.07174,
            354.52680,
            354.98190,
            355.43705,
            355.89223,
            356.34746,
            356.80273,
            357.25804,
            357.71340,
            358.16879,
            358.62423,
            359.07971,
            359.53523,
            359.99079,
            360.44640,
            360.90205,
            361.35773,
            361.81346,
            362.26924,
            362.72505,
            363.18091,
            363.63681,
            364.09275,
            364.54873,
            365.00475,
            365.46082,
            365.91692,
            366.37307,
            366.82926,
            367.28549,
            367.74177,
            368.19809,
            368.65444,
            369.11084,
            369.56728,
            370.02377,
            370.48029,
            370.93686,
            371.39347,
            371.85012,
            372.30681,
            372.76355,
            373.22032,
            373.67714,
            374.13400,
            374.59090,
            375.04785,
            375.50483,
            375.96186,
            376.41893,
            376.87604,
            377.33319,
            377.79039,
            378.24762,
            378.70490,
            379.16222,
            379.61958,
            380.07698,
            380.53443,
            380.99191,
            381.44944,
            381.90701,
            382.36463,
            382.82228,
            383.27997,
            383.73771,
            384.19549,
            384.65331,
            385.11117,
            385.56908,
            386.02703,
            386.48501,
            386.94304,
            387.40111,
            387.85923,
            388.31738,
            388.77558,
            389.23382,
            389.69210,
            390.15042,
            390.60878,
            391.06719,
            391.52564,
            391.98413,
            392.44266,
            392.90123,
            393.35984,
            393.81850,
            394.27720,
            394.73594,
            395.19472,
            395.65354,
            396.11240,
            396.57131,
            397.03026,
            397.48925,
            397.94828,
            398.40735,
            398.86647,
            399.32562,
            399.78482,
            400.24406,
            400.70334,
            401.16267,
            401.62203,
            402.08144,
            402.54089,
            403.00038,
            403.45991,
            403.91948,
            404.37910,
            404.83875,
            405.29845,
            405.75819,
            406.21798,
            406.67780,
            407.13767,
            407.59757,
            408.05752,
            408.51751,
            408.97754,
            409.43762,
            409.89773,
            410.35789,
            410.81809,
            411.27833,
            411.73861,
            412.19894,
            412.65930,
            413.11971,
            413.58016,
            414.04065,
            414.50118,
            414.96175,
            415.42237,
            415.88303,
            416.34372,
            416.80447,
            417.26525,
            417.72607,
            418.18694,
            418.64784,
            419.10879,
            419.56978,
            420.03081,
            420.49189,
            420.95300,
            421.41416,
            421.87536,
            422.33660,
            422.79788,
            423.25920,
            423.72057,
            424.18198,
            424.64342,
            425.10491,
            425.56644,
            426.02802,
            426.48963,
            426.95129,
            427.41299,
            427.87473,
            428.33651,
            428.79833,
            429.26019,
            429.72210,
            430.18405,
            430.64604,
            431.10807,
            431.57014,
            432.03225,
            432.49441,
            432.95661,
            433.41885,
            433.88113,
            434.34345,
            434.80581,
            435.26822,
            435.73066,
            436.19315,
            436.65568,
            437.11825,
            437.58087,
            438.04352,
            438.50622,
            438.96895,
            439.43173,
            439.89455,
            440.35742,
            440.82032,
            441.28327,
            441.74625,
            442.20928,
            442.67235,
            443.13546,
            443.59862,
            444.06181,
            444.52505,
            444.98833,
            445.45165,
            445.91501,
            446.37841,
            446.84185,
            447.30534,
            447.76887,
            448.23243,
            448.69605,
            449.15970,
            449.62339,
            450.08713,
            450.55090,
            451.01472,
            451.47858,
            451.94248,
            452.40642,
            452.87041,
            453.33443,
            453.79850,
            454.26261,
            454.72676,
            455.19095,
            455.65518,
            456.11946,
            456.58377,
            457.04813,
            457.51253,
            457.97697,
            458.44145,
            458.90598,
            459.37054,
            459.83515,
            460.29980,
            460.76449,
            461.22922,
            461.69399,
            462.15880,
            462.62366,
            463.08856,
            463.55350,
            464.01848,
            464.48350,
            464.94856,
            465.41366,
            465.87881,
            466.34400,
            466.80923,
            467.27450,
            467.73981,
            468.20516,
            468.67056,
            469.13599,
            469.60147,
            470.06699,
            470.53255,
            470.99815,
            471.46380,
            471.92948,
            472.39521,
            472.86097,
            473.32678,
            473.79263,
            474.25853,
            474.72446,
            475.19044,
            475.65645,
            476.12251,
            476.58861,
            477.05475,
            477.52093,
            477.98716,
            478.45342,
            478.91973,
            479.38608,
            479.85246,
            480.31890,
            480.78537,
            481.25188,
            481.71844,
            482.18503,
            482.65167,
            483.11835,
            483.58507,
            484.05183,
            484.51864,
            484.98548,
            485.45237,
            485.91930,
            486.38626,
            486.85328,
            487.32033,
            487.78742,
            488.25456,
            488.72173,
            489.18895,
            489.65621,
            490.12351,
            490.59085,
            491.05823,
            491.52566,
            491.99312,
            492.46063,
            492.92818,
            493.39577,
            493.86340,
            494.33107,
            494.79879,
            495.26654,
            495.73434,
            496.20218,
            496.67006,
            497.13798,
            497.60594,
            498.07394,
            498.54199,
            499.01007,
            499.47820,
            499.94637,
            500.41458,
            500.88283,
            501.35113,
            501.81946,
            502.28784,
            502.75625,
            503.22471,
            503.69321,
            504.16175,
            504.63034,
            505.09896,
            505.56762,
            506.03633,
            506.50508,
            506.97387,
            507.44270,
            507.91157,
            508.38048,
            508.84944,
            509.31843,
            509.78747,
            510.25655,
            510.72567,
            511.19483,
            511.66403,
            512.13328,
            512.60256,
            513.07189,
            513.54125,
            514.01066,
            514.48011,
            514.94960,
            515.41914,
            515.88871,
            516.35833,
            516.82798,
            517.29768,
            517.76742,
            518.23720,
            518.70702,
            519.17689,
            519.64679,
            520.11674,
            520.58672,
            521.05675,
            521.52682,
            521.99693,
            522.46708,
            522.93728,
            523.40751,
            523.87779,
            524.34810,
            524.81846,
            525.28886,
            525.75930,
            526.22978,
            526.70031,
            527.17087,
            527.64148,
            528.11212,
            528.58281,
            529.05354,
            529.52431,
            529.99513,
            530.46598,
            530.93687,
            531.40781,
            531.87879,
            532.34981,
            532.82086,
            533.29197,
            533.76311,
            534.23429,
            534.70552,
            535.17678,
            535.64809,
            536.11944,
            536.59083,
            537.06226,
            537.53373,
            538.00524,
            538.47680,
            538.94839,
            539.42003,
            539.89171,
            540.36342,
            540.83518,
            541.30699,
            541.77883,
            542.25071,
            542.72264,
            543.19460,
            543.66661,
            544.13866,
            544.61075,
            545.08288,
            545.55505,
            546.02727,
            546.49952,
            546.97182,
            547.44416,
            547.91653,
            548.38895,
            548.86141,
            549.33392,
            549.80646,
            550.27904,
            550.75167,
            551.22433,
            551.69704,
            552.16979,
            552.64258,
            553.11541,
            553.58828,
            554.06120,
            554.53415,
            555.00715,
            555.48018,
            555.95326,
            556.42638,
            556.89954,
            557.37274,
            557.84599,
            558.31927,
            558.79260,
            559.26596,
            559.73937,
            560.21282,
            560.68631,
            561.15984,
            561.63341,
            562.10702,
            562.58068,
            563.05437,
            563.52811,
            564.00188,
            564.47570,
            564.94956,
            565.42346,
            565.89741,
            566.37139,
            566.84541,
            567.31948,
            567.79358,
            568.26773,
            568.74192,
            569.21615,
            569.69042,
            570.16473,
            570.63909,
            571.11348,
            571.58792,
            572.06239,
            572.53691,
            573.01147,
            573.48607,
            573.96071,
            574.43539,
            574.91011,
            575.38488,
            575.85968,
            576.33453,
            576.80942,
            577.28434,
            577.75931,
            578.23432,
            578.70938,
            579.18447,
            579.65960,
            580.13478,
            580.60999,
            581.08525,
            581.56055,
            582.03589,
            582.51127,
            582.98669,
            583.46215,
            583.93765,
            584.41320,
            584.88878,
            585.36441,
            585.84008,
            586.31578,
            586.79153,
            587.26732,
            587.74316,
            588.21903,
            588.69494,
            589.17090,
            589.64689,
            590.12293,
            590.59901,
            591.07513,
            591.55129,
            592.02749,
            592.50373,
            592.98001,
            593.45634,
            593.93270,
            594.40911,
            594.88556,
            595.36204,
            595.83857,
            596.31514,
            596.79175,
            597.26841,
            597.74510,
            598.22183,
            598.69861,
            599.17542,
            599.65228,
            600.12918,
            600.60612,
            601.08310,
            601.56012,
            602.03718,
            602.51429,
            602.99143,
            603.46861,
            603.94584,
            604.42311,
            604.90042,
            605.37776,
            605.85515,
            606.33259,
            606.81006,
            607.28757,
            607.76512,
            608.24272,
            608.72036,
            609.19803,
            609.67575,
            610.15351,
            610.63131,
            611.10915,
            611.58703,
            612.06495,
            612.54292,
            613.02092,
            613.49897,
            613.97705,
            614.45518,
            614.93335,
            615.41156,
            615.88981,
            616.36810,
            616.84643,
            617.32481,
            617.80322,
            618.28167,
            618.76017,
            619.23871,
            619.71728,
            620.19590,
            620.67456,
            621.15326,
            621.63200,
            622.11079,
            622.58961,
            623.06847,
            623.54738,
            624.02633,
            624.50531,
            624.98434,
            625.46341,
            625.94252,
            626.42167,
            626.90086,
            627.38009,
            627.85937,
            628.33868,
            628.81804,
            629.29743,
            629.77687,
            630.25635,
            630.73587,
            631.21542,
            631.69503,
            632.17467,
            632.65435,
            633.13407,
            633.61384,
            634.09364,
            634.57349,
            635.05337,
            635.53330,
            636.01327,
            636.49328,
            636.97333,
            637.45342,
            637.93355,
            638.41372,
            638.89394,
            639.37419,
            639.85449,
            640.33482,
            640.81520,
            641.29562,
            641.77608,
            642.25658,
            642.73712,
            643.21770,
            643.69832,
            644.17898,
            644.65969,
            645.14043,
            645.62122,
            646.10204,
            646.58291,
            647.06382,
            647.54477,
            648.02576,
            648.50679,
            648.98786,
            649.46897,
            649.95013,
            650.43132,
            650.91255,
            651.39383,
            651.87515,
            652.35650,
            652.83790,
            653.31934,
            653.80082,
            654.28234,
            654.76390,
            655.24550,
            655.72715,
            656.20883,
            656.69055,
            657.17232,
            657.65412,
            658.13597,
            658.61786,
            659.09979,
            659.58176,
            660.06377,
            660.54582,
            661.02791,
            661.51004,
            661.99221,
            662.47443,
            662.95668,
            663.43898,
            663.92132,
            664.40369,
            664.88611,
            665.36857,
            665.85107,
            666.33361,
            666.81619,
            667.29881,
            667.78147,
            668.26418,
            668.74692,
            669.22971,
            669.71253,
            670.19540,
            670.67830,
            671.16125,
            671.64424,
            672.12727,
            672.61034,
            673.09345,
            673.57660,
            674.05979,
            674.54303,
            675.02630,
            675.50961,
            675.99297,
            676.47637,
            676.95980,
            677.44328,
            677.92680,
            678.41036,
            678.89396,
            679.37760,
            679.86128,
            680.34500,
            680.82876,
            681.31257,
            681.79641,
            682.28029,
            682.76422,
            683.24819,
            683.73219,
            684.21624,
            684.70033,
            685.18446,
            685.66863,
            686.15284,
            686.63709,
            687.12138,
            687.60571,
            688.09008,
            688.57450,
            689.05895,
            689.54345,
            690.02798,
            690.51256,
            690.99718,
            691.48184,
            691.96653,
            692.45127,
            692.93605,
            693.42087,
            693.90573,
            694.39064,
            694.87558,
            695.36056,
            695.84559,
            696.33065,
            696.81576,
            697.30090,
            697.78609,
            698.27132,
            698.75658,
            699.24189,
            699.72724,
            700.21263,
            700.69806,
            701.18353,
            701.66905,
            702.15460,
            702.64019,
            703.12583,
            703.61150,
            704.09721,
            704.58297,
            705.06877,
            705.55460,
            706.04048,
            706.52640,
            707.01236,
            707.49836,
            707.98440,
            708.47048,
            708.95660,
            709.44276,
            709.92896,
            710.41521,
            710.90149,
            711.38782,
            711.87418,
            712.36059,
            712.84703,
            713.33352,
            713.82005,
            714.30662,
            714.79322,
            715.27987,
            715.76656,
            716.25329,
            716.74007,
            717.22688,
            717.71373,
            718.20062,
            718.68756,
            719.17453,
            719.66154,
            720.14860,
            720.63570,
            721.12283,
            721.61001,
            722.09723,
            722.58448,
            723.07178,
            723.55912,
            724.04650,
            724.53392,
            725.02138,
            725.50889,
            725.99643,
            726.48401,
            726.97163,
            727.45930,
            727.94700,
            728.43475,
            728.92253,
            729.41036,
            729.89823,
            730.38613,
            730.87408,
            731.36207,
            731.85010,
            732.33817,
            732.82628,
            733.31443,
            733.80262,
            734.29085,
            734.77912,
            735.26743,
            735.75579,
            736.24418,
            736.73261,
            737.22109,
            737.70960,
            738.19816,
            738.68676,
            739.17539,
            739.66407,
            740.15279,
            740.64155,
            741.13035,
            741.61919,
            742.10806,
            742.59699,
            743.08595,
            743.57495,
            744.06399,
            744.55307,
            745.04219,
            745.53136,
            746.02056,
            746.50981,
            746.99909,
            747.48842,
            747.97778,
            748.46719,
            748.95664,
            749.44612,
            749.93565,
            750.42522,
            750.91483,
            751.40448,
            751.89417,
            752.38390,
            752.87367,
            753.36348,
            753.85333,
            754.34322,
            754.83316,
            755.32313,
            755.81314,
            756.30320,
            756.79329,
            757.28342,
            757.77360,
            758.26382,
            758.75407,
            759.24437,
            759.73471,
            760.22508,
            760.71550,
            761.20596,
            761.69646,
            762.18700,
            762.67758,
            763.16820,
            763.65886,
            764.14956,
            764.64030,
            765.13108,
            765.62191,
            766.11277,
            766.60367,
            767.09462,
            767.58560,
            768.07662,
            768.56769,
            769.05879,
            769.54994,
            770.04113,
            770.53235,
            771.02362,
            771.51493,
            772.00628,
            772.49766,
            772.98909,
            773.48056,
            773.97207,
            774.46362,
            774.95521,
            775.44684,
            775.93851,
            776.43022,
            776.92198,
            777.41377,
            777.90560,
            778.39747,
            778.88939,
            779.38134,
            779.87334,
            780.36537,
            780.85744,
            781.34956,
            781.84172,
            782.33391,
            782.82615,
            783.31842,
            783.81074,
            784.30310,
            784.79550,
            785.28794,
            785.78041,
            786.27293,
            786.76549,
            787.25809,
            787.75073,
            788.24341,
            788.73613,
            789.22889,
            789.72170,
            790.21454,
            790.70742,
            791.20034,
            791.69331,
            792.18631,
            792.67935,
            793.17244,
            793.66556,
            794.15872,
            794.65193,
            795.14517,
            795.63846,
            796.13179,
            796.62515,
            797.11856,
            797.61201,
            798.10549,
            798.59902,
            799.09259,
            799.58620,
            800.07984,
            800.57353,
            801.06726,
            801.56103,
            802.05484,
            802.54869,
            803.04258,
            803.53651,
            804.03048,
            804.52449,
            805.01854,
            805.51264,
            806.00677,
            806.50094,
            806.99515,
            807.48941,
            807.98370,
            808.47803,
            808.97241,
            809.46682,
            809.96127,
            810.45577,
            810.95030,
            811.44488,
            811.93949,
            812.43415,
            812.92885,
            813.42358,
            813.91836,
            814.41318,
            814.90803,
            815.40293,
            815.89787,
            816.39285,
            816.88786,
            817.38292,
            817.87802,
            818.37316,
            818.86834,
            819.36356,
            819.85882,
            820.35412,
            820.84946,
            821.34484,
            821.84026,
            822.33572,
            822.83122,
    };
}
