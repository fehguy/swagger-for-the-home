#import <Foundation/Foundation.h>
#import "SWGObject.h"


@interface SWGZWaveDevice : SWGObject

@property(nonatomic) NSNumber* _id;  

@property(nonatomic) NSString* name;  

@property(nonatomic) NSString* type;  

- (id) _id: (NSNumber*) _id
     name: (NSString*) name
     type: (NSString*) type;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

