#import "SWGDate.h"
#import "SWGTemperatureForecast.h"
#import "SWGWeatherDescription.h"

@implementation SWGTemperatureForecast

-(id)dt: (NSNumber*) dt
    temp: (SWGTemperature*) temp
    pressure: (NSNumber*) pressure
    humidity: (NSNumber*) humidity
    weather: (NSArray*) weather
    speed: (NSNumber*) speed
    deg: (NSNumber*) deg
    clouds: (NSNumber*) clouds
{
  _dt = dt;
  _temp = temp;
  _pressure = pressure;
  _humidity = humidity;
  _weather = weather;
  _speed = speed;
  _deg = deg;
  _clouds = clouds;
  return self;
}

-(id) initWithValues:(NSDictionary*)dict
{
    self = [super init];
    if(self) {
        _dt = dict[@"dt"]; 
        id temp_dict = dict[@"temp"];
        if(temp_dict != nil)
            _temp = [[SWGTemperature alloc]initWithValues:temp_dict];
        _pressure = dict[@"pressure"]; 
        _humidity = dict[@"humidity"]; 
        id weather_dict = dict[@"weather"];
        if([weather_dict isKindOfClass:[NSArray class]]) {

            NSMutableArray * objs = [[NSMutableArray alloc] initWithCapacity:[(NSArray*)weather_dict count]];

            if([(NSArray*)weather_dict count] > 0) {
                for (NSDictionary* dict in (NSArray*)weather_dict) {
                    SWGWeatherDescription* d = [[SWGWeatherDescription alloc] initWithValues:dict];
                    [objs addObject:d];
                }
                
                _weather = [[NSArray alloc] initWithArray:objs];
            }
            else {
                _weather = [[NSArray alloc] init];
            }
        }
        else {
            _weather = [[NSArray alloc] init];
        }
        _speed = dict[@"speed"]; 
        _deg = dict[@"deg"]; 
        _clouds = dict[@"clouds"]; 
        

    }
    return self;
}

-(NSDictionary*) asDictionary {
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    if(_dt != nil) dict[@"dt"] = _dt ;
        if(_temp != nil){
        if([_temp isKindOfClass:[NSArray class]]){
            NSMutableArray * array = [[NSMutableArray alloc] init];
            for( SWGTemperature *temp in (NSArray*)_temp) {
                [array addObject:[(SWGObject*)temp asDictionary]];
            }
            dict[@"temp"] = array;
        }
        else if(_temp && [_temp isKindOfClass:[SWGDate class]]) {
            NSString * dateString = [(SWGDate*)_temp toString];
            if(dateString){
                dict[@"temp"] = dateString;
            }
        }
        else {
        if(_temp != nil) dict[@"temp"] = [(SWGObject*)_temp asDictionary];
        }
    }
    if(_pressure != nil) dict[@"pressure"] = _pressure ;
        if(_humidity != nil) dict[@"humidity"] = _humidity ;
        if(_weather != nil){
        if([_weather isKindOfClass:[NSArray class]]){
            NSMutableArray * array = [[NSMutableArray alloc] init];
            for( NSArray *weather in (NSArray*)_weather) {
                [array addObject:[(SWGObject*)weather asDictionary]];
            }
            dict[@"weather"] = array;
        }
        else if(_weather && [_weather isKindOfClass:[SWGDate class]]) {
            NSString * dateString = [(SWGDate*)_weather toString];
            if(dateString){
                dict[@"weather"] = dateString;
            }
        }
        else {
        if(_weather != nil) dict[@"weather"] = [(SWGObject*)_weather asDictionary];
        }
    }
    if(_speed != nil) dict[@"speed"] = _speed ;
        if(_deg != nil) dict[@"deg"] = _deg ;
        if(_clouds != nil) dict[@"clouds"] = _clouds ;
        NSDictionary* output = [dict copy];
    return output;
}

@end

