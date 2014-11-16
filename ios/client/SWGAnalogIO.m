#import "SWGDate.h"
#import "SWGAnalogIO.h"

@implementation SWGAnalogIO

-(id)name: (NSString*) name
    position: (NSNumber*) position
    value: (NSNumber*) value
    timestamp: (SWGDate*) timestamp
{
  _name = name;
  _position = position;
  _value = value;
  _timestamp = timestamp;
  return self;
}

-(id) initWithValues:(NSDictionary*)dict
{
    self = [super init];
    if(self) {
        _name = dict[@"name"]; 
        _position = dict[@"position"]; 
        _value = dict[@"value"]; 
        id timestamp_dict = dict[@"timestamp"];
        if(timestamp_dict != nil)
            _timestamp = [[SWGDate alloc]initWithValues:timestamp_dict];
        

    }
    return self;
}

-(NSDictionary*) asDictionary {
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    if(_name != nil) dict[@"name"] = _name ;
        if(_position != nil) dict[@"position"] = _position ;
        if(_value != nil) dict[@"value"] = _value ;
        if(_timestamp != nil){
        if([_timestamp isKindOfClass:[NSArray class]]){
            NSMutableArray * array = [[NSMutableArray alloc] init];
            for( SWGDate *timestamp in (NSArray*)_timestamp) {
                [array addObject:[(SWGObject*)timestamp asDictionary]];
            }
            dict[@"timestamp"] = array;
        }
        else if(_timestamp && [_timestamp isKindOfClass:[SWGDate class]]) {
            NSString * dateString = [(SWGDate*)_timestamp toString];
            if(dateString){
                dict[@"timestamp"] = dateString;
            }
        }
        else {
        if(_timestamp != nil) dict[@"timestamp"] = [(SWGObject*)_timestamp asDictionary];
        }
    }
    NSDictionary* output = [dict copy];
    return output;
}

@end

