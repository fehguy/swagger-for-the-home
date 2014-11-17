#import "SWGDate.h"
#import "SWGTemperature.h"

@implementation SWGTemperature

-(id)day: (NSNumber*) day
    min: (NSNumber*) min
    max: (NSNumber*) max
    night: (NSNumber*) night
    eve: (NSNumber*) eve
    morn: (NSNumber*) morn
{
  _day = day;
  _min = min;
  _max = max;
  _night = night;
  _eve = eve;
  _morn = morn;
  return self;
}

-(id) initWithValues:(NSDictionary*)dict
{
    self = [super init];
    if(self) {
        _day = dict[@"day"]; 
        _min = dict[@"min"]; 
        _max = dict[@"max"]; 
        _night = dict[@"night"]; 
        _eve = dict[@"eve"]; 
        _morn = dict[@"morn"]; 
        

    }
    return self;
}

-(NSDictionary*) asDictionary {
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    if(_day != nil) dict[@"day"] = _day ;
        if(_min != nil) dict[@"min"] = _min ;
        if(_max != nil) dict[@"max"] = _max ;
        if(_night != nil) dict[@"night"] = _night ;
        if(_eve != nil) dict[@"eve"] = _eve ;
        if(_morn != nil) dict[@"morn"] = _morn ;
        NSDictionary* output = [dict copy];
    return output;
}

@end

