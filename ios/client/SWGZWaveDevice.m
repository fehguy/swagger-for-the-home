#import "SWGDate.h"
#import "SWGZWaveDevice.h"

@implementation SWGZWaveDevice

-(id)_id: (NSNumber*) _id
    name: (NSString*) name
    type: (NSString*) type
{
  __id = _id;
  _name = name;
  _type = type;
  return self;
}

-(id) initWithValues:(NSDictionary*)dict
{
    self = [super init];
    if(self) {
        __id = dict[@"id"]; 
        _name = dict[@"name"]; 
        _type = dict[@"type"]; 
        

    }
    return self;
}

-(NSDictionary*) asDictionary {
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    if(__id != nil) dict[@"id"] = __id ;
        if(_name != nil) dict[@"name"] = _name ;
        if(_type != nil) dict[@"type"] = _type ;
        NSDictionary* output = [dict copy];
    return output;
}

@end

